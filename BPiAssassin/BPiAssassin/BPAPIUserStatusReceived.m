//
//  BPUserStatusReceived.m
//  BPiAssassin
//
//  Created by Robby Cohen on 4/7/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPAPIUserStatusReceived.h"

@implementation BPAPIUserStatusReceived
-(id)initWithDict:(NSDictionary *)dict {
    self = [super initWithDict:dict];
    if(self) {
        _status = [[dict objectForKey:@"status"] copy];
        _gameId = [dict objectForKey:@"gameId"] ? [[dict objectForKey:@"gameId"] copy] : nil;
    }
    return self;
}
@end
