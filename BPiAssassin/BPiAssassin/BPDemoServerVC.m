//
//  BPDemoServerVC.m
//  BPiAssassin
//
//  Created by Robby Cohen on 3/25/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPDemoServerVC.h"
#import "BPAPIClient.h"
#import "BPNotifications.h"
#import "BPAPIClientObjects.h"
@interface BPDemoServerVC ()
@property (nonatomic, retain) NSString* hostId;
@property (nonatomic, retain) NSMutableArray* everyonesId;
@property (nonatomic, retain) NSString* gameId;
@property (nonatomic, retain) NSString* target;
-(NSString *) genRandStringLength: (int) len;
@end

@implementation BPDemoServerVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
- (IBAction)registerUserBtnPress:(UIButton *)sender {
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(alert:) name:kUserRegisteredNotification object:[BPAPIClient sharedAPIClient]];
    
    
    [[BPAPIClient sharedAPIClient] registerUserForUsername:@"user1" forThumbnail:[self imageWithColor:[UIColor redColor]] forArrayOfFaceImages:@[[self imageWithColor:[UIColor redColor]],[self imageWithColor:[UIColor greenColor]],[self imageWithColor:[UIColor blueColor]]] forApnDeviceToken:@"apndevicetoken1"];
}
- (IBAction)registerMoreUsersBtnPress:(UIButton *)sender {
    [[BPAPIClient sharedAPIClient] registerUserForUsername:@"user2" forThumbnail:[self imageWithColor:[UIColor redColor]] forArrayOfFaceImages:@[[self imageWithColor:[UIColor redColor]],[self imageWithColor:[UIColor cyanColor]],[self imageWithColor:[UIColor blueColor]]] forApnDeviceToken:@"apndevicetoken2"];
    
    [[BPAPIClient sharedAPIClient] registerUserForUsername:@"user3" forThumbnail:[self imageWithColor:[UIColor redColor]] forArrayOfFaceImages:@[[self imageWithColor:[UIColor redColor]],[self imageWithColor:[UIColor blackColor]],[self imageWithColor:[UIColor blueColor]]] forApnDeviceToken:@"apndevicetoken3"];
    
    [[BPAPIClient sharedAPIClient] registerUserForUsername:@"user4" forThumbnail:[self imageWithColor:[UIColor redColor]] forArrayOfFaceImages:@[[self imageWithColor:[UIColor darkGrayColor]],[self imageWithColor:[UIColor greenColor]],[self imageWithColor:[UIColor blueColor]]] forApnDeviceToken:@"apndevicetoken4"];
    
}
- (IBAction)createAGame:(UIButton *)sender {
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(alert:) name:kGameCreatedNotification object:[BPAPIClient sharedAPIClient]];
    [[BPAPIClient sharedAPIClient] createGameWithHostId:self.hostId withAllPlayersId:self.everyonesId];
}
- (IBAction)startAGame:(UIButton *)sender {
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(alert:) name:kGameStartedNotification object:[BPAPIClient sharedAPIClient]];
    [[BPAPIClient sharedAPIClient] startGameWithGameId:self.gameId withMeanImage:[self genRandStringLength:240] withCovarEigen:[self genRandStringLength:240] withWorkFunctEigen:[self genRandStringLength:240] withProjectedImages:[self genRandStringLength:1000]];
}
- (IBAction)getTargets:(UIButton *)sender {
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(alert:) name:kTargetReceivedNotification object:[BPAPIClient sharedAPIClient]];
    [[BPAPIClient sharedAPIClient] getTargetForGameId:self.gameId forUserId:self.hostId];
}

- (IBAction)killTarget:(id)sender {
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(alert:) name:kUserKilledNotification object:[BPAPIClient sharedAPIClient]];
    [[BPAPIClient sharedAPIClient] killUserForGameId:self.gameId forAssassinId:self.hostId forVictimId:self.target];
    
}
- (IBAction)getGameplayData:(UIButton *)sender {
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(alert:)  name:kGamePlayDataReceivedNotification object:[BPAPIClient sharedAPIClient]];
    
    [[BPAPIClient sharedAPIClient] getGamePlayDataForGameId:self.gameId];
}

-(void)alert:(NSNotification*)notification {
    id APIClientObject = [[notification userInfo] objectForKey:@"event"];
    if([APIClientObject isKindOfClass:[BPAPIUserRegistered class]]) {
        if(!self.hostId) {
            self.hostId = [APIClientObject userId];
            self.everyonesId = [NSMutableArray new];
        }
        [self.everyonesId addObject:[APIClientObject userId]];
    } else if ([APIClientObject isKindOfClass:[BPAPIGameCreated class]]) {
        self.gameId  = [APIClientObject gameId];
    } else if([APIClientObject isKindOfClass:[BPAPINewTargetReceived class]]) {
        self.target = [APIClientObject targetId];
    } 
    
    NSLog(@"notified");
}




-(NSString *) genRandStringLength: (int) len {
    NSString *letters = @"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    NSMutableString *randomString = [NSMutableString stringWithCapacity: len];
    
    for (int i=0; i<len; i++) {
        [randomString appendFormat: @"%C", [letters characterAtIndex: arc4random() % [letters length]]];
    }
    
    return randomString;
}
     

- (UIImage *)imageWithColor:(UIColor *)color {
    
    CGRect rect = CGRectMake(0, 0, 100, 100);
    
    
    // Create a 100 by 100 pixel context
    
    UIGraphicsBeginImageContextWithOptions(rect.size, NO, 0);
    
    [color setFill];
    
    UIRectFill(rect);   // Fill it with your color
    
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    
    UIGraphicsEndImageContext();
    
    
    
    return image;
    
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
