//
//  BPNotificationClient.m
//  BPiAssassin
//
//  Created by Robby Cohen on 3/25/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPNotificationClient.h"
#import "BPNotifications.h"
#import "BPAPIClient.h"
@interface BPNotificationClient ()
+(void)helperSendNotification:(NSString*)notification withPayload:(NSDictionary*)payload;
@end
@implementation BPNotificationClient

+(void)helperSendNotification:(NSString *)notification withPayload:(NSDictionary*)payload {
    dispatch_async(dispatch_get_main_queue(),^{
        [[NSNotificationCenter defaultCenter] postNotificationName:notification object:[BPAPIClient sharedAPIClient] userInfo:payload];
    });
}

+(void)notifyUserRegistered:(NSDictionary*)userUUID {
    [BPNotificationClient helperSendNotification:kUserRegisteredNotification withPayload:userUUID ];
}
+(void)notifyGameCreated:(NSDictionary*)dictOfUsersImages {
    [BPNotificationClient helperSendNotification:kGameCreatedNotification withPayload:dictOfUsersImages];
}
+(void)notifyGameStarted {
    [BPNotificationClient helperSendNotification:kGameStartedNotification withPayload:nil];
}
+(void)notifyGamePlayDataReceived:(NSDictionary*)gamePlayData {
    [BPNotificationClient helperSendNotification:kGamePlayDataReceivedNotification withPayload:gamePlayData];
}
+(void)notifyTargetReceived:(NSDictionary*)target {
    [BPNotificationClient helperSendNotification:kTargetReceivedNotification withPayload:target];
}
+(void)notifyUserKilled:(NSDictionary*)newTarget {
    [BPNotificationClient helperSendNotification:kUserKilledNotification withPayload:newTarget];
}

+(void)notifyAPIClientFailed:(NSError *)error forNotification:(NSString *)notification {
    NSDictionary *errorDictionary = [NSDictionary dictionaryWithObjectsAndKeys:notification, @"NotificationName", error, @"Error", nil];
    [BPNotificationClient helperSendNotification:kAPIClientFailureNotification withPayload:errorDictionary];
}
@end
