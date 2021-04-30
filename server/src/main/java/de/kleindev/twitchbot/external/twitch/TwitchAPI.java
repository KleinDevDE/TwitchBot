package de.kleindev.twitchbot.external.twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import de.kleindev.twitchbot.external.twitch.listeners.*;

public class TwitchAPI {
    private TwitchClient twitchClient;
    private TwitchConfig twitchConfig;

    public TwitchAPI(Long userID){
        //TODO Get data from MySQL

        TwitchClientBuilder twitchClientBuilder = TwitchClientBuilder.builder();
        twitchClientBuilder.withEnableChat(true);
        twitchClientBuilder.withEnableHelix(true);
        twitchClientBuilder.withEnableGraphQL(true);
        twitchClientBuilder.withEnablePubSub(true);
        twitchClientBuilder.withEnableKraken(true);
        twitchClientBuilder.withCommandTrigger(twitchConfig.getCommandPrefix());
        twitchClientBuilder.withChatAccount(twitchConfig.getOAuth2Credential());
        twitchClient = twitchClientBuilder.build();
    }

    public void startBot(){
        twitchClient.getChat().joinChannel(twitchConfig.getStreamerChannelName());
        twitchClient.getChat().sendMessage(twitchConfig.getStreamerChannelName(), "/color "+twitchConfig.getChatNickColor());
        twitchClient.getPubSub().listenForChannelPointsRedemptionEvents(twitchConfig.getOAuth2Credential(), twitchConfig.getStreamerChannelID());
        twitchClient.getPubSub().listenForCheerEvents(twitchConfig.getOAuth2Credential(), twitchConfig.getStreamerChannelID());
        twitchClient.getPubSub().listenForPublicCheerEvents(twitchConfig.getOAuth2Credential(), twitchConfig.getStreamerChannelID());
        twitchClient.getPubSub().listenForChannelBitsLeaderboardEvents(twitchConfig.getOAuth2Credential(), twitchConfig.getStreamerChannelID());

        // Register all listeners for "recent activity log"
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new FollowEventListener());
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new ClearChatEventListener());
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new ChannelStateEventListener());
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new GiftSubscriptionsEventListener());
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new ChannelLeaveEventListener());
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new HostOnEventListener());
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new UserBanEventListener());
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new DonationEventListener());
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new ChannelNoticeEventListener());
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new UserTimeoutEventListener());
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new RaidEventListener());
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new ChannelMessageActionEventListener());
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new ChannelMessageEventListener());
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new SubscriptionEventListener());
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new ChannelModEventListener());
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new ChannelJoinEventListener());
        twitchClient.getChat().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new CheerEventListener());
        twitchClient.getPubSub().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new BitsLeaderboardEventListener());
        twitchClient.getPubSub().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new ChannelPointsRedemptionEventListener());
        twitchClient.getPubSub().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new RedemptionStatusUpdateEventListener());
        twitchClient.getPubSub().getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new ChannelBitsEventListener());
        //TODO add whisper listener
    }

    public void stopBot(){
        twitchClient.getPubSub().getEventManager().close();
    }


    public void sendPublicMessage(String message){
        twitchClient.getChat().sendMessage(twitchConfig.getStreamerChannelName(), message);
    }

    public void sendPrivateMessage(String receiver, String message){
        twitchClient.getChat().sendPrivateMessage(receiver, message);
    }

    public void sendMeMessage(String message){ //Like /me <message>
        twitchClient.getChat().sendMessage(twitchConfig.getStreamerChannelName(), "/me " + message);
    }

    public void sendWhisperMessage(String receiver, String message){ //Like /me <message>
        twitchClient.getChat().sendMessage(twitchConfig.getStreamerChannelName(), "/w " + receiver + " " +  message);    }

    public void clearChat(){
        twitchClient.getChat().sendMessage(twitchConfig.getStreamerChannelName(), "/clear");
    }

    public void follow(Long userID, boolean goLiveNotification){
        twitchClient.getGraphQL().followUser(twitchConfig.getOAuth2Credential(), userID, goLiveNotification);
    }

    public void unFollow(Long userID){
        twitchClient.getGraphQL().unfollowUser(twitchConfig.getOAuth2Credential(), userID);
    }

    public void follow(OAuth2Credential oAuth2Credential, Long userID, boolean goLiveNotification){
        twitchClient.getGraphQL().followUser(oAuth2Credential, userID, goLiveNotification);
    }

    public void unFollow(OAuth2Credential oAuth2Credential, Long userID){
        twitchClient.getGraphQL().unfollowUser(oAuth2Credential, userID);
    }
}
