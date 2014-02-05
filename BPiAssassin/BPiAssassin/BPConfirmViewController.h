//
//  BPConfirmViewController.h
//  BPiAssassin
//
//  Created by John Rozier on 2/4/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BPConfirmViewController : UIViewController

@property (strong, nonatomic) UIImage *face;
@property (strong, nonatomic) NSString *faceName;
@property (strong, nonatomic) IBOutlet UIImageView *imageView;
@property (strong, nonatomic) IBOutlet UILabel *welcomeLabel;


@end
