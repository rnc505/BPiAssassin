//
//  BPAPIClient.m
//  BPiAssassin
//
//  Created by Robby Cohen on 3/24/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPAPIClient.h"
#import "BPNotificationClient.h"
#import "BPNotifications.h"

@interface BPAPIClient ()
-(void)updateUserStatus:(NSString*)status;
//-(void)updateUserStatus;
@end

@implementation BPAPIClient

static NSString * const kBaseUrl = @"http://iassassin-cs279.appspot.com/game/";


+(BPAPIClient*)sharedAPIClient {
    static BPAPIClient *_sharedAPIClient = nil;
    
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _sharedAPIClient = [[self alloc] initWithBaseURL:[NSURL URLWithString:kBaseUrl]];
    });
    
    return _sharedAPIClient;
}

- (instancetype)initWithBaseURL:(NSURL *)url
{
    self = [super initWithBaseURL:url];
    
    if (self) {
        self.responseSerializer = [AFJSONResponseSerializer serializer];
        self.requestSerializer = [AFJSONRequestSerializer serializer];
        NSMutableSet *superset = [NSMutableSet setWithSet:self.responseSerializer.acceptableContentTypes];
        [superset addObject:@"text/plain"];
        self.responseSerializer.acceptableContentTypes = [superset copy];
    }
    
    return self;
}

#pragma mark - User
-(void)registerUserForUsername:(NSString*)username forThumbnail:(UIImage*)thumbnail forArrayOfFaceImages:(NSArray*)arrayOfImgs forApnDeviceToken:(NSString*)token withRegisteredId:(NSString *)registeredId {
    
    NSMutableArray* faceImages = [NSMutableArray arrayWithCapacity:[arrayOfImgs count]];
    for (UIImage* img in arrayOfImgs) {
        NSString* base64EncodedImage = [UIImagePNGRepresentation(img) base64EncodedStringWithOptions:NSDataBase64Encoding64CharacterLineLength];
//        NSString* base64EncodedImage = @"dalfkjasdkalsdfjkladf";
        [faceImages addObject:base64EncodedImage];
    }
    
    NSDictionary* param = [NSDictionary dictionaryWithObjectsAndKeys:
                           username,@"username",
                           [UIImagePNGRepresentation(thumbnail)base64EncodedStringWithOptions:NSDataBase64Encoding64CharacterLineLength],@"thumbnail",
//                           @"fakdsljflksadjfklasdfjklasdjfklajsdf",@"thumbnail",
                           faceImages,@"faceImages",
                           token, @"apn",
                           @"iOS", @"platformId",
                           registeredId, @"registeredId",
                           nil];
    
    [self POST:@"registerUser" parameters:param success:^(NSURLSessionDataTask *task, id responseObject) {
        [self updateUserStatus:@"Registered"];
        [BPNotificationClient notifyUserRegistered:responseObject];
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [BPNotificationClient notifyAPIClientFailed:error forNotification:kUserRegisteredNotification];
    }];
}

-(void)getUserStatusForId:(NSString *)userId {
    [self GET:[NSString stringWithFormat:@"getUserStatus/%@",userId] parameters:nil success:^(NSURLSessionDataTask *task, id responseObject) {
        [BPNotificationClient notifyUserStatusRecieved:responseObject];
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [BPNotificationClient notifyAPIClientFailed:error forNotification:kUserStatusReceiviedNotification];
    }];
}

#pragma mark - Game Hoster
-(void)createGameWithHostId:(NSString*)hostId withAllPlayersId:(NSArray*)playersIds {
    
    NSDictionary* param = [NSDictionary dictionaryWithObjectsAndKeys:
                           hostId,@"hostId",
                           playersIds,@"playerIds",
                           nil];
    
    
    [self POST:@"createGame" parameters:param success:^(NSURLSessionDataTask *task, id responseObject) {
        [BPNotificationClient notifyGameCreated:responseObject];
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [BPNotificationClient notifyAPIClientFailed:error forNotification:kGameCreatedNotification];
    }];
    
}
-(void)startGameWithGameId:(NSString*)gameId withMeanImage:(NSString*)meanImage withCovarEigen:(NSString*)covarEigen withWorkFunctEigen:(NSString*)workFunctEigen withProjectedImages:(NSString*)projectedImages {
    
    NSDictionary* param = [NSDictionary dictionaryWithObjectsAndKeys:
                              meanImage, @"meanImage",
                              covarEigen, @"covarEigen",
                              workFunctEigen, @"workFunctEigen",
                              projectedImages, @"projectedImages",
                              gameId,@"gameId",
                              nil];
    
    [self POST:@"startGame" parameters:param success:^(NSURLSessionDataTask *task, id responseObject) {
        [BPNotificationClient notifyGameStarted];
        [self updateUserStatus:@"Playing - Alive"];
        [self getUserStatusForId:[[NSUserDefaults standardUserDefaults] objectForKey:@"myUUID"]];
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [BPNotificationClient notifyAPIClientFailed:error forNotification:kGameStartedNotification];
    }];
    
    
    
}

#pragma mark - Game Non-Hoster
-(void)getGamePlayDataForGameId:(NSString*)gameId {
    [self GET:[NSString stringWithFormat:@"gamePlayData/%@",gameId] parameters:nil success:^(NSURLSessionDataTask *task, id responseObject) {
        [BPNotificationClient notifyGamePlayDataReceived:responseObject];
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [BPNotificationClient notifyAPIClientFailed:error forNotification:kGamePlayDataReceivedNotification];
    }];
}

#pragma mark - Game Player
-(void)getTargetForGameId:(NSString*)gameId forUserId:(NSString*)userId {
    [self GET:[NSString stringWithFormat:@"getTarget/%@/%@",gameId,userId] parameters:nil success:^(NSURLSessionDataTask *task, id responseObject) {
        [BPNotificationClient notifyTargetReceived:responseObject];
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [BPNotificationClient notifyAPIClientFailed:error forNotification:kTargetReceivedNotification];
    }];
}
-(void)killUserForGameId:(NSString*)gameId forAssassinId:(NSString*)assassinID forVictimId:(NSString*)victimId {
    NSDictionary* param = [NSDictionary dictionaryWithObjectsAndKeys:
                           gameId,@"gameId",
                           assassinID, @"assassinId",
                           victimId, @"victimId",
                           nil];
    
    [self POST:@"killUser" parameters:param success:^(NSURLSessionDataTask *task, id responseObject) {
        [BPNotificationClient notifyUserKilled:responseObject];
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [BPNotificationClient notifyAPIClientFailed:error forNotification:kUserKilledNotification];
    }];
}

-(void)updateUserStatus:(NSString *)status {
    [[NSUserDefaults standardUserDefaults] setObject:status forKey:@"CurrentUserState"];
    [[NSUserDefaults standardUserDefaults] synchronize];
}


@end
