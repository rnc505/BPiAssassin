//
//  BPAPINewTargetReceived.h
//  BPiAssassin
//
//  Created by Robby Cohen on 4/1/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPAPIClientObject.h"

@interface BPAPINewTargetReceived : BPAPIClientObject
@property (nonatomic, copy) NSString* targetCodename;
@property (nonatomic, copy) NSString* targetId;
@property (nonatomic) UIImage* targetThumbnail;
-(id)initWithDict:(NSDictionary *)dict;

@end
