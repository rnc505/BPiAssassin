//
//  BPInitialViewController.h
//  BPiAssassin
//
//  Created by John Rozier on 2/3/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BPInitialViewController : UIViewController

@property (strong, nonatomic) IBOutlet UIImageView *face1;
@property (strong, nonatomic) IBOutlet UITextField *faceName;


- (IBAction)takePhoto:(UIButton *)sender;
- (IBAction)selectPhoto:(UIButton *)sender;

@end
