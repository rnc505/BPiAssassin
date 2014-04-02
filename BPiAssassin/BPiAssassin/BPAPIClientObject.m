//
//  BPAPIClientObject.m
//  BPiAssassin
//
//  Created by Robby Cohen on 4/1/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPAPIClientObject.h"

@implementation BPAPIClientObject
+(id)initializeWithDictionary:(NSDictionary *)dictionary {
    return [[self alloc] initWithDict:dictionary];
}

-(id)initWithDict:(NSDictionary *)dict {
    self = [super init];
    return self;
}
@end
