//
//  BPGameStatsVC.m
//  BPiAssassin
//
//  Created by John Rozier on 2/15/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPGameStatsVC.h"

@interface BPGameStatsVC ()

@end

@implementation BPGameStatsVC

+ (id)allocWithRouterParams:(NSDictionary *)params {
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main_iPhone" bundle:[NSBundle mainBundle]];
    BPGameStatsVC *instance = [storyboard instantiateViewControllerWithIdentifier:@"BPGameStatsVC"];
    
    return instance;
}
- (IBAction)backButtonPressed:(id)sender {
    [[Routable sharedRouter] open:@"gameInProgressHome"];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
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

@end
