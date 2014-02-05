//
//  BPConfirmViewController.m
//  BPiAssassin
//
//  Created by John Rozier on 2/4/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPConfirmViewController.h"

@interface BPConfirmViewController ()

@end

@implementation BPConfirmViewController

@synthesize face, imageView, welcomeLabel, faceName;

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
    [self.imageView setImage:face];
    
    NSString *welcomeMessage = @"Phone Unlocked: Welcome back ";
    welcomeMessage = [welcomeMessage stringByAppendingString:self.faceName];
    [self.welcomeLabel setText:welcomeMessage];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
