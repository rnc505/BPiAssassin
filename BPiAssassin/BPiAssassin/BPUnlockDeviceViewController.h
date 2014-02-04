//
//  BPUnlockDeviceViewController.h
//  BPiAssassin
//
//  Created by John Rozier on 2/3/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BPUnlockDeviceViewController : UIViewController

@property (strong, nonatomic) IBOutlet UIImageView *targetFace;
@property (strong, nonatomic) IBOutlet UILabel *welcomeLabel;


- (IBAction)takePhoto:(UIButton *)sender;
- (IBAction)selectPhoto:(UIButton *)sender;


@end
