//
//  BPAPIClient.h
//  BPiAssassin
//
//  Created by Robby Cohen on 3/24/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "AFHTTPSessionManager.h"

@interface BPAPIClient : AFHTTPSessionManager
+(BPAPIClient*)sharedAPIClient;
- (instancetype)initWithBaseURL:(NSURL *)url;

#pragma mark - User
-(void)registerUserForUsername:(NSString*)username forThumbnail:(UIImage*)thumbnail forArrayOfFaceImages:(NSArray*)arrayOfImgs forApnDeviceToken:(NSString*)token withRegisteredId:(NSString*)registeredId;
-(void)getUserStatusForId:(NSString*)userId;

#pragma mark - Game Hoster
-(void)createGameWithHostId:(NSString*)hostId withAllPlayersId:(NSArray*)playersIds;
-(void)startGameWithGameId:(NSString*)gameId withMeanImage:(NSString*)meanImage withCovarEigen:(NSString*)covarEigen withWorkFunctEigen:(NSString*)workFunctEigen withProjectedImages:(NSString*)projectedImages;

#pragma mark - Game Non-Hoster
-(void)getGamePlayDataForGameId:(NSString*)gameId;

#pragma mark - Game Player
-(void)getTargetForGameId:(NSString*)gameId forUserId:(NSString*)userId;
-(void)killUserForGameId:(NSString*)gameId forAssassinId:(NSString*)assassinID forVictimId:(NSString*)victimId;


@end
