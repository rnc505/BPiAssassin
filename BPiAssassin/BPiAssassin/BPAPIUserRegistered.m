//
//  BPAPIUser.m
//  BPiAssassin
//
//  Created by Robby Cohen on 4/1/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPAPIUserRegistered.h"

@implementation BPAPIUserRegistered
-(id)initWithDict:(NSDictionary *)dict {
    self = [super initWithDict:dict];
    if(self) {
        _userId = [[dict objectForKey:@"userId"] copy];
    }
    return self;
}
@end
