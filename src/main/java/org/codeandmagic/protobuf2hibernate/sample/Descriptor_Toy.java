// Generated by the protocol buffer compiler.  DO NOT EDIT!

package org.codeandmagic.protobuf2hibernate.sample;

public final class Descriptor_Toy {
  private Descriptor_Toy() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  static com.google.protobuf.Descriptors.Descriptor
    internal_static_domain_Toy_descriptor;
  static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_domain_Toy_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\ttoy.proto\022\006domain\"&\n\003Toy\022\021\n\townerUuid\030" +
      "\002 \002(\t\022\014\n\004name\030\003 \002(\tB@\n*org.codeandmagic." +
      "protobuf2hibernate.sampleB\016Descriptor_To" +
      "yH\001P\001"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_domain_Toy_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_domain_Toy_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_domain_Toy_descriptor,
              new java.lang.String[] { "OwnerUuid", "Name", },
              org.codeandmagic.protobuf2hibernate.sample.Toy.class,
              org.codeandmagic.protobuf2hibernate.sample.Toy.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  public static void internalForceInit() {}
}
