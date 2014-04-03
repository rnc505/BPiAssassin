//
//  BPAPIGameCreated.m
//  BPiAssassin
//
//  Created by Robby Cohen on 4/1/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPAPIGameCreated.h"

@implementation BPAPIGameCreated
-(id)initWithDict:(NSDictionary *)dict {
    self = [super initWithDict:dict];
    if(self) {
        _gameId = [[dict objectForKey:@"gameUUID"] copy];
        NSMutableArray *mutable = [NSMutableArray new];
        NSDictionary* people = [dict objectForKey:@"usrImageCompilation"];
        [people enumerateKeysAndObjectsUsingBlock:^(NSString* uuid, NSArray* personsImages, BOOL *stop) {
            BPPerson* newPerson = [BPPerson personWithName:uuid];
            [personsImages enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
                [newPerson detectFaceAndAddImage:[UIImage imageWithData:[[NSData alloc] initWithBase64EncodedString:[[obj objectForKey:@"image"] objectForKey:@"value"] options:NSDataBase64DecodingIgnoreUnknownCharacters]]];
            }];
            [mutable addObject:newPerson];
        }];
        _images = [mutable copy];
    }
    return self;
}
@end
