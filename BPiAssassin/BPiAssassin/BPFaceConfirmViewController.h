//
//  BPFaceConfirmViewController.h
//  BPiAssassin
//
//  Created by John Rozier on 2/3/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BPFaceConfirmViewController : UIViewController

@property (strong, nonatomic) IBOutlet UIImageView *face2;
@property (strong, nonatomic) IBOutlet UILabel *nameDisplay;

- (IBAction)takePhoto:(UIButton *)sender;
- (IBAction)selectPhoto:(UIButton *)sender;


@end
