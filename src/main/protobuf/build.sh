#!/bin/sh

PC=$PROTOBUF_COMPILER
export DYLD_LIBRARY_PATH=$PC/../.libs
test -x "$PC" || exec echo "PROTOBUF_COMPILER ($PC) not found, will not try to compile the protobufs"

echo "Using protobuf compiler at $PC"

cd $(dirname "$0")
P=$PWD
J=$P/../java

PROTO_PATH=$P
INC0=$(dirname "$PC")
if test -d $INC0/google/protobuf/; then PROTO_PATH=$PROTO_PATH:$INC0; fi
INC0=/usr/local/include/
if test -d $INC0/google/protobuf/; then PROTO_PATH=$PROTO_PATH:$INC0; fi

for p in cat person toy; do
#for p in integrationgroup; do
	$PC --java_out=$J --proto_path=$PROTO_PATH $P/$p.proto
done
