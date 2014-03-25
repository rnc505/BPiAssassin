//
//  BPNotificationClient.h
//  BPiAssassin
//
//  Created by Robby Cohen on 3/25/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface BPNotificationClient : NSObject
+(void)notifyUserRegistered:(NSString*)userUUID;
+(void)notifyGameCreated:(NSDictionary*)dictOfUsersImages;
+(void)notifyGameStarted;
+(void)notifyGamePlayDataReceived:(NSDictionary*)gamePlayData;
+(void)notifyTargetReceived:(NSString*)target;
+(void)notifyUserKilled:(NSString*)newTarget;

+(void)notifyAPIClientFailed:(NSError*)error forNotification:(NSString*)notification;
@end
