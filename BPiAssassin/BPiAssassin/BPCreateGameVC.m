//
//  BPCreateGameVC.m
//  BPiAssassin
//
//  Created by John Rozier on 2/15/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPCreateGameVC.h"

@interface BPCreateGameVC ()

@end

@implementation BPCreateGameVC
@synthesize createOrRegisterBtn;

+ (id)allocWithRouterParams:(NSDictionary *)params {
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main_iPhone" bundle:[NSBundle mainBundle]];
    BPCreateGameVC *instance = [storyboard instantiateViewControllerWithIdentifier:@"BPCreateGameVC"];
    
    return instance;
}

- (id)initWithRouterParams:(NSDictionary *)params {
    if ((self = [self initWithNibName:nil bundle:nil])) {
    }
    return self;
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
- (IBAction)createOrRegisterBtnPressed:(id)sender {
    if([[NSUserDefaults standardUserDefaults] objectForKey:@"myUUID"]) {
//        [self performSegueWithIdentifier:@"createGameRequested" sender:self];
        [[Routable sharedRouter] open:@"matchmakingPage"];
    } else {
//        [self performSegueWithIdentifier:@"registerRequested" sender:self];
        [[Routable sharedRouter] open:@"registerPage"];

    }
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    if(self.navigationController) {
        [self.navigationController setNavigationBarHidden:YES];
    }
}

-(void)viewWillAppear:(BOOL)animated {
    if([[NSUserDefaults standardUserDefaults] objectForKey:@"myUUID"]) {
        [self.createOrRegisterBtn setTitle:@"Create Game" forState:(UIControlStateNormal)];
    } else {
        [self.createOrRegisterBtn setTitle:@"Register User" forState:(UIControlStateNormal)];
    }
}

-(void)viewDidAppear:(BOOL)animated {
    //Handles different start screens depending on the current game state
//    if ([[NSUserDefaults standardUserDefaults] boolForKey:@"gameInProgress"]) {
//        [self performSegueWithIdentifier:@"openedApp_GameInProgress" sender:self];
//    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
