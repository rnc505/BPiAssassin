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
#import "BPAPIClientObjects.h"
@interface BPNotificationClient ()
+(void)helperSendNotification:(NSString*)notification withPayload:(id)payload;
@end
@implementation BPNotificationClient

+(void)helperSendNotification:(NSString *)notification withPayload:(id)payload {
    dispatch_async(dispatch_get_main_queue(),^{
        [[NSNotificationCenter defaultCenter] postNotificationName:notification object:[BPAPIClient sharedAPIClient] userInfo:[NSDictionary dictionaryWithObject:payload forKey:@"event"]];
    });
}

+(void)notifyUserRegistered:(NSDictionary*)userUUID {
    [BPNotificationClient helperSendNotification:kUserRegisteredNotification withPayload:[BPAPIUserRegistered initializeWithDictionary:userUUID] ];
}
+(void)notifyGameCreated:(NSDictionary*)dictOfUsersImages {
    [BPNotificationClient helperSendNotification:kGameCreatedNotification withPayload:[BPAPIGameCreated initializeWithDictionary:dictOfUsersImages]];
}
+(void)notifyGameStarted {
    [BPNotificationClient helperSendNotification:kGameStartedNotification withPayload:[NSNull null]];
}
+(void)notifyGamePlayDataReceived:(NSDictionary*)gamePlayData {
    [BPNotificationClient helperSendNotification:kGamePlayDataReceivedNotification withPayload:[BPAPIGameplayDataReceived initializeWithDictionary:gamePlayData]];
}
+(void)notifyTargetReceived:(NSDictionary*)target {
    [BPNotificationClient helperSendNotification:kTargetReceivedNotification withPayload:[BPAPINewTargetReceived initializeWithDictionary:target]];
}
+(void)notifyUserKilled:(NSDictionary*)newTarget {
    [BPNotificationClient helperSendNotification:kUserKilledNotification withPayload:[BPAPINewTargetReceived initializeWithDictionary:newTarget]];
}

+(void)notifyAPIClientFailed:(NSError *)error forNotification:(NSString *)notification {
    NSDictionary *errorDictionary = [NSDictionary dictionaryWithObjectsAndKeys:notification, @"NotificationName", error, @"Error", nil];
    [BPNotificationClient helperSendNotification:kAPIClientFailureNotification withPayload:errorDictionary];
}
@end
