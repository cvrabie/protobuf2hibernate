// Generated by the protocol buffer compiler.  DO NOT EDIT!

package org.codeandmagic.protobuf2hibernate.sample;

public final class Descriptor_Cat {
  private Descriptor_Cat() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  static com.google.protobuf.Descriptors.Descriptor
    internal_static_domain_Cat_descriptor;
  static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_domain_Cat_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014sample.proto\022\006domain\"X\n\003Cat\022\014\n\004uuid\030\001 " +
      "\002(\t\022\017\n\007created\030\002 \002(\003\022\014\n\004name\030\003 \002(\t\022\023\n\013ha" +
      "ir_length\030\004 \001(\002\022\017\n\007kittens\030\005 \003(\tB@\n*org." +
      "codeandmagic.protobuf2hibernate.sampleB\016" +
      "Descriptor_CatH\001P\001"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_domain_Cat_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_domain_Cat_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_domain_Cat_descriptor,
              new java.lang.String[] { "Uuid", "Created", "Name", "HairLength", "Kittens", },
              org.codeandmagic.protobuf2hibernate.sample.Cat.class,
              org.codeandmagic.protobuf2hibernate.sample.Cat.Builder.class);
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