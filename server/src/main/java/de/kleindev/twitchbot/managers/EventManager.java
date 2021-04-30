package de.kleindev.twitchbot.managers;

import com.google.inject.Binder;
import com.google.inject.Module;
import de.kleindev.twitchbot.external.twitch.events.Async;
import de.kleindev.twitchbot.external.twitch.events.CriticalProcessTimeEvent;
import de.kleindev.twitchbot.external.twitch.events.Event;
import de.kleindev.twitchbot.external.twitch.events.ExceptionEvent;
import de.kleindev.twitchbot.external.twitch.listeners.EventHandler;
import de.kleindev.twitchbot.external.twitch.listeners.EventPriority;
import de.kleindev.twitchbot.external.twitch.listeners.Listener;
import de.kleindev.twitchbot.logging.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class EventManager implements Module {

    @Override
    public void configure(Binder binder) {

    }

    public abstract class RegisteredListener {

        public abstract void call(Event e);

    }

    private class OneLineListener<T extends Event> extends RegisteredListener {

        Consumer<T> cons;
        boolean async = false;

        public OneLineListener(Consumer<T> cons2) {
            this.cons = cons2;
        }

        public OneLineListener(Consumer<T> cons, boolean async) {
            this.cons = cons;
            this.async = async;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void call(Event e) {
            Runnable r = ()->{
                cons.accept((T) e);
            };
            if(async) {
                service.submit(r);
            } else {
                r.run();
            }

        }

    }

    private class MethodBasedListener extends RegisteredListener {

        Object o;
        Method m;
        boolean async;

        public MethodBasedListener(Object o, Method m) {
            this.o = o;
            this.m = m;
            async = m.isAnnotationPresent(Async.class);
        }

        public void call(Event e) {
            Runnable r = ()->{
                try {
                    m.setAccessible(true);
                    m.invoke(o, e);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
                    onException(e, e1);
                }
            };
            if(async) {
                service.submit(r);
            } else {
                r.run();
            }
        }

    }



    List<HashMap<Class<? extends Event>, Set<RegisteredListener>>> listeners;
    List<BiConsumer<Event, Exception>> exceptionHandlers;
    HashMap<Class<? extends Event>, Long> criticalTime;
    ExecutorService service;

    public EventManager() {
        listeners = new ArrayList<HashMap<Class<? extends Event>, Set<RegisteredListener>>>();
        for(int i = 0; i < EventPriority.values().length; i++) {
            HashMap<Class<? extends Event>, Set<RegisteredListener>> hm = new HashMap<Class<? extends Event>, Set<RegisteredListener>>();
            listeners.add(hm);
        }
        exceptionHandlers = new ArrayList<BiConsumer<Event,Exception>>();
        criticalTime = new HashMap<Class<? extends Event>, Long>();
        service = Executors.newCachedThreadPool();
    }

    public void setCriticalTime(Class<? extends Event> event, long time, TimeUnit unit) {
        criticalTime.put(event, unit.toMillis(time));
    }

    public void setCriticalTime(Class<? extends Event> event, long time) {
        criticalTime.put(event, time);
    }

    private void onException(Event e, Exception ex) {
        for(BiConsumer<Event, Exception> handler : exceptionHandlers) {
            handler.accept(e, ex);
        }
        if(!(e instanceof ExceptionEvent)) {
            ExceptionEvent event = new ExceptionEvent(ex);
            callEvent(event);
            if(event.isPrintStackTrace()) {
                ex.printStackTrace();
            }
        } else {
            ex.printStackTrace();
        }

    }

    public void addExceptionHandler(BiConsumer<Event, Exception> handler) {
        exceptionHandlers.add(handler);
    }

    @SuppressWarnings("unchecked")
    public void registerListener(Listener listener) {
        for(Method m : listener.getClass().getMethods()) {
            if(!m.isAnnotationPresent(EventHandler.class)) continue;
            if(m.getParameterCount() != 1) continue;
            if(!Event.class.isAssignableFrom(m.getParameterTypes()[0])) continue;

            EventPriority priority = m.getAnnotation(EventHandler.class).priority();
            Class<? extends Event> eventClazz = (Class<? extends Event>) m.getParameterTypes()[0];
            MethodBasedListener registeredListener = new MethodBasedListener(listener, m);
            HashMap<Class<? extends Event>, Set<RegisteredListener>> rmap = listeners.get(priority.ordinal());
            if(!rmap.containsKey(eventClazz)) {
                rmap.put(eventClazz, new HashSet<RegisteredListener>());
            }
            rmap.get(eventClazz).add(registeredListener);
        }
    }

    public <T extends Event> RegisteredListener register(Class<T> clazz, EventPriority priority, Consumer<T> cons) {
        OneLineListener<T> l = new OneLineListener<T>(cons);
        HashMap<Class<? extends Event>, Set<RegisteredListener>> rmap = listeners.get(priority.ordinal());
        if(!rmap.containsKey(clazz)) {
            rmap.put(clazz, new HashSet<RegisteredListener>());
        }
        rmap.get(clazz).add(l);
        return l;
    }

    public <T extends Event> RegisteredListener register(Class<T> clazz, EventPriority priority, boolean async, Consumer<T> cons) {
        OneLineListener<T> l = new OneLineListener<T>(cons, async);
        HashMap<Class<? extends Event>, Set<RegisteredListener>> rmap = listeners.get(priority.ordinal());
        if(!rmap.containsKey(clazz)) {
            rmap.put(clazz, new HashSet<RegisteredListener>());
        }
        rmap.get(clazz).add(l);
        return l;
    }

    public void unregisterListener(Listener listener) {
        for(HashMap<Class<? extends Event>, Set<RegisteredListener>> hm : listeners) {
            for(Map.Entry<Class<? extends Event>, Set<RegisteredListener>> en : hm.entrySet()) {
                Set<RegisteredListener> rl = null;
                for(RegisteredListener l : en.getValue()) {
                    if(l instanceof MethodBasedListener) {
                        if(((MethodBasedListener)l).o == listener) {
                            if(rl == null) rl = new HashSet<RegisteredListener>();
                            rl.add(l);
                        }
                    }
                }
                if(rl != null) {
                    en.getValue().removeAll(rl);
                }
            }
        }
    }

    public void unregisterListener(RegisteredListener listener) {
        for(HashMap<Class<? extends Event>, Set<RegisteredListener>> hm : listeners) {
            for(Map.Entry<Class<? extends Event>, Set<RegisteredListener>> en : hm.entrySet()) {
                Set<RegisteredListener> rl = null;
                for(RegisteredListener l : en.getValue()) {
                    if(l == listener) {
                        if(rl == null) rl = new HashSet<RegisteredListener>();
                        rl.add(l);
                    }
                }
                if(rl != null) {
                    en.getValue().removeAll(rl);
                }
            }
        }
    }

    public <T extends Event> T callEvent(T e) {
        try {
            long start = System.currentTimeMillis();
            for(HashMap<Class<? extends Event>, Set<RegisteredListener>> hm : listeners) {
                Set<RegisteredListener> listeners = hm.get(e.getClass());
                if(listeners != null) {
                    for(RegisteredListener list : listeners) {
                        list.call(e);
                    }
                }
            }
            long time = System.currentTimeMillis()-start;
            long maxTime = criticalTime.getOrDefault(e.getClass(), -1L);
            if(maxTime > 0 && time > maxTime) {
                CriticalProcessTimeEvent event = new CriticalProcessTimeEvent(e, time);
                callEvent(event);
                if(event.shouldBroadcast) {
                    Logger.error("[Event] "+e.getClass().getSimpleName() + " took " + time + " ms to process!");
                }
            }
        } catch(Exception ex) {
            onException(e, ex);
        }
        return e;
    }

}