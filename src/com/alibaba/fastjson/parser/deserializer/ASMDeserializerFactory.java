package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.asm.ASMException;
import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.FieldVisitor;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.SymbolTable;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.DeserializeBeanInfo;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

public class ASMDeserializerFactory
  implements Opcodes
{
  private static final ASMDeserializerFactory instance = new ASMDeserializerFactory();
  private ASMClassLoader classLoader = new ASMClassLoader();
  private final AtomicLong seed = new AtomicLong();

  private void _batchSet(Context paramContext, MethodVisitor paramMethodVisitor)
  {
    _batchSet(paramContext, paramMethodVisitor, true);
  }

  private void _batchSet(Context paramContext, MethodVisitor paramMethodVisitor, boolean paramBoolean)
  {
    int i = 0;
    int j = paramContext.getFieldInfoList().size();
    while (i < j)
    {
      Label localLabel = new Label();
      if (paramBoolean)
        _isFlag(paramMethodVisitor, paramContext, i, localLabel);
      _loadAndSet(paramContext, paramMethodVisitor, (FieldInfo)paramContext.getFieldInfoList().get(i));
      if (paramBoolean)
        paramMethodVisitor.visitLabel(localLabel);
      i++;
    }
  }

  private void _createInstance(ClassWriter paramClassWriter, Context paramContext)
  {
    MethodVisitor localMethodVisitor = paramClassWriter.visitMethod(1, "createInstance", "(" + ASMUtils.getDesc(DefaultJSONParser.class) + ASMUtils.getDesc(java.lang.reflect.Type.class) + ")Ljava/lang/Object;", null, null);
    localMethodVisitor.visitTypeInsn(187, ASMUtils.getType(paramContext.getClazz()));
    localMethodVisitor.visitInsn(89);
    localMethodVisitor.visitMethodInsn(183, ASMUtils.getType(paramContext.getClazz()), "<init>", "()V");
    localMethodVisitor.visitInsn(176);
    localMethodVisitor.visitMaxs(3, 3);
    localMethodVisitor.visitEnd();
  }

  private void _createInstance(Context paramContext, MethodVisitor paramMethodVisitor)
  {
    if (Modifier.isPublic(paramContext.getBeanInfo().getDefaultConstructor().getModifiers()))
    {
      paramMethodVisitor.visitTypeInsn(187, ASMUtils.getType(paramContext.getClazz()));
      paramMethodVisitor.visitInsn(89);
      paramMethodVisitor.visitMethodInsn(183, ASMUtils.getType(paramContext.getClazz()), "<init>", "()V");
      paramMethodVisitor.visitVarInsn(58, paramContext.var("instance"));
      return;
    }
    paramMethodVisitor.visitVarInsn(25, 0);
    paramMethodVisitor.visitVarInsn(25, 1);
    paramMethodVisitor.visitMethodInsn(183, ASMUtils.getType(ASMJavaBeanDeserializer.class), "createInstance", "(" + ASMUtils.getDesc(DefaultJSONParser.class) + ")Ljava/lang/Object;");
    paramMethodVisitor.visitTypeInsn(192, ASMUtils.getType(paramContext.getClazz()));
    paramMethodVisitor.visitVarInsn(58, paramContext.var("instance"));
  }

  private void _deserObject(Context paramContext, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Class<?> paramClass)
  {
    _getFieldDeser(paramContext, paramMethodVisitor, paramFieldInfo);
    paramMethodVisitor.visitVarInsn(25, 1);
    if ((paramFieldInfo.getFieldType() instanceof Class))
      paramMethodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(paramFieldInfo.getFieldClass())));
    while (true)
    {
      paramMethodVisitor.visitLdcInsn(paramFieldInfo.getName());
      paramMethodVisitor.visitMethodInsn(185, ASMUtils.getType(ObjectDeserializer.class), "deserialze", "(" + ASMUtils.getDesc(DefaultJSONParser.class) + ASMUtils.getDesc(java.lang.reflect.Type.class) + "Ljava/lang/Object;)Ljava/lang/Object;");
      paramMethodVisitor.visitTypeInsn(192, ASMUtils.getType(paramClass));
      paramMethodVisitor.visitVarInsn(58, paramContext.var(paramFieldInfo.getName() + "_asm"));
      return;
      paramMethodVisitor.visitVarInsn(25, 0);
      paramMethodVisitor.visitLdcInsn(paramFieldInfo.getName());
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(ASMJavaBeanDeserializer.class), "getFieldType", "(Ljava/lang/String;)Ljava/lang/reflect/Type;");
    }
  }

  private void _deserialize_endCheck(Context paramContext, MethodVisitor paramMethodVisitor, Label paramLabel)
  {
    Label localLabel = new Label();
    paramMethodVisitor.visitIntInsn(21, paramContext.var("matchedCount"));
    paramMethodVisitor.visitJumpInsn(158, paramLabel);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "token", "()I");
    paramMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "RBRACE", "I");
    paramMethodVisitor.visitJumpInsn(160, paramLabel);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
    paramMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "COMMA", "I");
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "nextToken", "(I)V");
    paramMethodVisitor.visitLabel(localLabel);
  }

  private void _deserialze_list_obj(Context paramContext, MethodVisitor paramMethodVisitor, Label paramLabel, FieldInfo paramFieldInfo, Class<?> paramClass1, Class<?> paramClass2, int paramInt)
  {
    Label localLabel1 = new Label();
    Label localLabel2 = new Label();
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "matchField", "([C)Z");
    paramMethodVisitor.visitJumpInsn(154, localLabel1);
    paramMethodVisitor.visitInsn(1);
    paramMethodVisitor.visitVarInsn(58, paramContext.var(paramFieldInfo.getName() + "_asm"));
    paramMethodVisitor.visitJumpInsn(167, localLabel2);
    paramMethodVisitor.visitLabel(localLabel1);
    _setFlag(paramMethodVisitor, paramContext, paramInt);
    Label localLabel3 = new Label();
    paramMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "token", "()I");
    paramMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "NULL", "I");
    paramMethodVisitor.visitJumpInsn(160, localLabel3);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
    paramMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "COMMA", "I");
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "nextToken", "(I)V");
    paramMethodVisitor.visitInsn(1);
    paramMethodVisitor.visitTypeInsn(192, ASMUtils.getType(paramClass1));
    paramMethodVisitor.visitVarInsn(58, paramContext.var(paramFieldInfo.getName() + "_asm"));
    paramMethodVisitor.visitLabel(localLabel3);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "token", "()I");
    paramMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "LBRACKET", "I");
    paramMethodVisitor.visitJumpInsn(160, paramLabel);
    _getCollectionFieldItemDeser(paramContext, paramMethodVisitor, paramFieldInfo, paramClass2);
    paramMethodVisitor.visitMethodInsn(185, ASMUtils.getType(ObjectDeserializer.class), "getFastMatchToken", "()I");
    paramMethodVisitor.visitVarInsn(54, paramContext.var("fastMatchToken"));
    paramMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("fastMatchToken"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "nextToken", "(I)V");
    _newCollection(paramMethodVisitor, paramClass1);
    paramMethodVisitor.visitVarInsn(58, paramContext.var(paramFieldInfo.getName() + "_asm"));
    paramMethodVisitor.visitVarInsn(25, 1);
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "getContext", "()" + ASMUtils.getDesc(ParseContext.class));
    paramMethodVisitor.visitVarInsn(58, paramContext.var("listContext"));
    paramMethodVisitor.visitVarInsn(25, 1);
    paramMethodVisitor.visitVarInsn(25, paramContext.var(paramFieldInfo.getName() + "_asm"));
    paramMethodVisitor.visitLdcInsn(paramFieldInfo.getName());
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)" + ASMUtils.getDesc(ParseContext.class));
    paramMethodVisitor.visitInsn(87);
    Label localLabel4 = new Label();
    Label localLabel5 = new Label();
    paramMethodVisitor.visitInsn(3);
    paramMethodVisitor.visitVarInsn(54, paramContext.var("i"));
    paramMethodVisitor.visitLabel(localLabel4);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "token", "()I");
    paramMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "RBRACKET", "I");
    paramMethodVisitor.visitJumpInsn(159, localLabel5);
    paramMethodVisitor.visitVarInsn(25, 0);
    paramMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), paramFieldInfo.getName() + "_asm_list_item_deser__", ASMUtils.getDesc(ObjectDeserializer.class));
    paramMethodVisitor.visitVarInsn(25, 1);
    paramMethodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(paramClass2)));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("i"));
    paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(Integer.class), "valueOf", "(I)Ljava/lang/Integer;");
    paramMethodVisitor.visitMethodInsn(185, ASMUtils.getType(ObjectDeserializer.class), "deserialze", "(" + ASMUtils.getDesc(DefaultJSONParser.class) + "Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
    paramMethodVisitor.visitVarInsn(58, paramContext.var("list_item_value"));
    paramMethodVisitor.visitIincInsn(paramContext.var("i"), 1);
    paramMethodVisitor.visitVarInsn(25, paramContext.var(paramFieldInfo.getName() + "_asm"));
    paramMethodVisitor.visitVarInsn(25, paramContext.var("list_item_value"));
    if (paramClass1.isInterface())
      paramMethodVisitor.visitMethodInsn(185, ASMUtils.getType(paramClass1), "add", "(Ljava/lang/Object;)Z");
    while (true)
    {
      paramMethodVisitor.visitInsn(87);
      paramMethodVisitor.visitVarInsn(25, 1);
      paramMethodVisitor.visitVarInsn(25, paramContext.var(paramFieldInfo.getName() + "_asm"));
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "checkListResolve", "(Ljava/util/Collection;)V");
      paramMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "token", "()I");
      paramMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "COMMA", "I");
      paramMethodVisitor.visitJumpInsn(160, localLabel4);
      paramMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
      paramMethodVisitor.visitVarInsn(21, paramContext.var("fastMatchToken"));
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "nextToken", "(I)V");
      paramMethodVisitor.visitJumpInsn(167, localLabel4);
      paramMethodVisitor.visitLabel(localLabel5);
      paramMethodVisitor.visitVarInsn(25, 1);
      paramMethodVisitor.visitVarInsn(25, paramContext.var("listContext"));
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "setContext", "(" + ASMUtils.getDesc(ParseContext.class) + ")V");
      paramMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "token", "()I");
      paramMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "RBRACKET", "I");
      paramMethodVisitor.visitJumpInsn(160, paramLabel);
      paramMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
      paramMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "COMMA", "I");
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "nextToken", "(I)V");
      paramMethodVisitor.visitLabel(localLabel2);
      return;
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(paramClass1), "add", "(Ljava/lang/Object;)Z");
    }
  }

  private void _deserialze_obj(Context paramContext, MethodVisitor paramMethodVisitor, Label paramLabel, FieldInfo paramFieldInfo, Class<?> paramClass, int paramInt)
  {
    Label localLabel1 = new Label();
    Label localLabel2 = new Label();
    paramMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
    paramMethodVisitor.visitVarInsn(25, 0);
    paramMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), paramFieldInfo.getName() + "_asm_prefix__", "[C");
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "matchField", "([C)Z");
    paramMethodVisitor.visitJumpInsn(154, localLabel1);
    paramMethodVisitor.visitInsn(1);
    paramMethodVisitor.visitVarInsn(58, paramContext.var(paramFieldInfo.getName() + "_asm"));
    paramMethodVisitor.visitJumpInsn(167, localLabel2);
    paramMethodVisitor.visitLabel(localLabel1);
    _setFlag(paramMethodVisitor, paramContext, paramInt);
    paramMethodVisitor.visitVarInsn(21, paramContext.var("matchedCount"));
    paramMethodVisitor.visitInsn(4);
    paramMethodVisitor.visitInsn(96);
    paramMethodVisitor.visitVarInsn(54, paramContext.var("matchedCount"));
    _deserObject(paramContext, paramMethodVisitor, paramFieldInfo, paramClass);
    paramMethodVisitor.visitVarInsn(25, 1);
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "getResolveStatus", "()I");
    paramMethodVisitor.visitFieldInsn(178, ASMUtils.getType(DefaultJSONParser.class), "NeedToResolve", "I");
    paramMethodVisitor.visitJumpInsn(160, localLabel2);
    paramMethodVisitor.visitVarInsn(25, 1);
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "getLastResolveTask", "()" + ASMUtils.getDesc(DefaultJSONParser.ResolveTask.class));
    paramMethodVisitor.visitVarInsn(58, paramContext.var("resolveTask"));
    paramMethodVisitor.visitVarInsn(25, paramContext.var("resolveTask"));
    paramMethodVisitor.visitVarInsn(25, 1);
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "getContext", "()" + ASMUtils.getDesc(ParseContext.class));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.ResolveTask.class), "setOwnerContext", "(" + ASMUtils.getDesc(ParseContext.class) + ")V");
    paramMethodVisitor.visitVarInsn(25, paramContext.var("resolveTask"));
    paramMethodVisitor.visitVarInsn(25, 0);
    paramMethodVisitor.visitLdcInsn(paramFieldInfo.getName());
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(ASMJavaBeanDeserializer.class), "getFieldDeserializer", "(Ljava/lang/String;)" + ASMUtils.getDesc(FieldDeserializer.class));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.ResolveTask.class), "setFieldDeserializer", "(" + ASMUtils.getDesc(FieldDeserializer.class) + ")V");
    paramMethodVisitor.visitVarInsn(25, 1);
    paramMethodVisitor.visitFieldInsn(178, ASMUtils.getType(DefaultJSONParser.class), "NONE", "I");
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "setResolveStatus", "(I)V");
    paramMethodVisitor.visitLabel(localLabel2);
  }

  private void _getCollectionFieldItemDeser(Context paramContext, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Class<?> paramClass)
  {
    Label localLabel = new Label();
    paramMethodVisitor.visitVarInsn(25, 0);
    paramMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), paramFieldInfo.getName() + "_asm_list_item_deser__", ASMUtils.getDesc(ObjectDeserializer.class));
    paramMethodVisitor.visitJumpInsn(199, localLabel);
    paramMethodVisitor.visitVarInsn(25, 0);
    paramMethodVisitor.visitVarInsn(25, 1);
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "getConfig", "()" + ASMUtils.getDesc(ParserConfig.class));
    paramMethodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(paramClass)));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(ParserConfig.class), "getDeserializer", "(" + ASMUtils.getDesc(java.lang.reflect.Type.class) + ")" + ASMUtils.getDesc(ObjectDeserializer.class));
    paramMethodVisitor.visitFieldInsn(181, paramContext.getClassName(), paramFieldInfo.getName() + "_asm_list_item_deser__", ASMUtils.getDesc(ObjectDeserializer.class));
    paramMethodVisitor.visitLabel(localLabel);
    paramMethodVisitor.visitVarInsn(25, 0);
    paramMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), paramFieldInfo.getName() + "_asm_list_item_deser__", ASMUtils.getDesc(ObjectDeserializer.class));
  }

  private void _getFieldDeser(Context paramContext, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo)
  {
    Label localLabel = new Label();
    paramMethodVisitor.visitVarInsn(25, 0);
    paramMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), paramFieldInfo.getName() + "_asm_deser__", ASMUtils.getDesc(ObjectDeserializer.class));
    paramMethodVisitor.visitJumpInsn(199, localLabel);
    paramMethodVisitor.visitVarInsn(25, 0);
    paramMethodVisitor.visitVarInsn(25, 1);
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "getConfig", "()" + ASMUtils.getDesc(ParserConfig.class));
    paramMethodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(paramFieldInfo.getFieldClass())));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(ParserConfig.class), "getDeserializer", "(" + ASMUtils.getDesc(java.lang.reflect.Type.class) + ")" + ASMUtils.getDesc(ObjectDeserializer.class));
    paramMethodVisitor.visitFieldInsn(181, paramContext.getClassName(), paramFieldInfo.getName() + "_asm_deser__", ASMUtils.getDesc(ObjectDeserializer.class));
    paramMethodVisitor.visitLabel(localLabel);
    paramMethodVisitor.visitVarInsn(25, 0);
    paramMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), paramFieldInfo.getName() + "_asm_deser__", ASMUtils.getDesc(ObjectDeserializer.class));
  }

  private void _init(ClassWriter paramClassWriter, Context paramContext)
  {
    int i = 0;
    int j = paramContext.getFieldInfoList().size();
    while (i < j)
    {
      FieldInfo localFieldInfo3 = (FieldInfo)paramContext.getFieldInfoList().get(i);
      paramClassWriter.visitField(1, localFieldInfo3.getName() + "_asm_prefix__", "[C").visitEnd();
      i++;
    }
    int k = 0;
    int m = paramContext.getFieldInfoList().size();
    if (k < m)
    {
      FieldInfo localFieldInfo2 = (FieldInfo)paramContext.getFieldInfoList().get(k);
      Class localClass = localFieldInfo2.getFieldClass();
      if (localClass.isPrimitive());
      while (true)
      {
        k++;
        break;
        if (!localClass.isEnum())
          if (Collection.class.isAssignableFrom(localClass))
            paramClassWriter.visitField(1, localFieldInfo2.getName() + "_asm_list_item_deser__", ASMUtils.getDesc(ObjectDeserializer.class)).visitEnd();
          else
            paramClassWriter.visitField(1, localFieldInfo2.getName() + "_asm_deser__", ASMUtils.getDesc(ObjectDeserializer.class)).visitEnd();
      }
    }
    MethodVisitor localMethodVisitor = paramClassWriter.visitMethod(1, "<init>", "(" + ASMUtils.getDesc(ParserConfig.class) + ASMUtils.getDesc(Class.class) + ")V", null, null);
    localMethodVisitor.visitVarInsn(25, 0);
    localMethodVisitor.visitVarInsn(25, 1);
    localMethodVisitor.visitVarInsn(25, 2);
    localMethodVisitor.visitMethodInsn(183, ASMUtils.getType(ASMJavaBeanDeserializer.class), "<init>", "(" + ASMUtils.getDesc(ParserConfig.class) + ASMUtils.getDesc(Class.class) + ")V");
    localMethodVisitor.visitVarInsn(25, 0);
    localMethodVisitor.visitFieldInsn(180, ASMUtils.getType(ASMJavaBeanDeserializer.class), "serializer", ASMUtils.getDesc(ASMJavaBeanDeserializer.InnerJavaBeanDeserializer.class));
    localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JavaBeanDeserializer.class), "getFieldDeserializerMap", "()" + ASMUtils.getDesc(Map.class));
    localMethodVisitor.visitInsn(87);
    int n = 0;
    int i1 = paramContext.getFieldInfoList().size();
    while (n < i1)
    {
      FieldInfo localFieldInfo1 = (FieldInfo)paramContext.getFieldInfoList().get(n);
      localMethodVisitor.visitVarInsn(25, 0);
      localMethodVisitor.visitLdcInsn("\"" + localFieldInfo1.getName() + "\":");
      localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(String.class), "toCharArray", "()" + ASMUtils.getDesc([C.class));
      localMethodVisitor.visitFieldInsn(181, paramContext.getClassName(), localFieldInfo1.getName() + "_asm_prefix__", "[C");
      n++;
    }
    localMethodVisitor.visitInsn(177);
    localMethodVisitor.visitMaxs(4, 4);
    localMethodVisitor.visitEnd();
  }

  private void _isEnable(Context paramContext, MethodVisitor paramMethodVisitor, Feature paramFeature)
  {
    paramMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
    paramMethodVisitor.visitFieldInsn(178, ASMUtils.getType(Feature.class), paramFeature.name(), "L" + ASMUtils.getType(Feature.class) + ";");
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "isEnabled", "(L" + ASMUtils.getType(Feature.class) + ";" + ")Z");
  }

  private void _loadAndSet(Context paramContext, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo)
  {
    Class localClass = paramFieldInfo.getFieldClass();
    java.lang.reflect.Type localType = paramFieldInfo.getFieldType();
    if (localClass == Boolean.TYPE)
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("instance"));
      paramMethodVisitor.visitVarInsn(21, paramContext.var(paramFieldInfo.getName() + "_asm"));
      _set(paramContext, paramMethodVisitor, paramFieldInfo);
    }
    do
    {
      return;
      if ((localClass == Byte.TYPE) || (localClass == Short.TYPE) || (localClass == Integer.TYPE) || (localClass == Character.TYPE))
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("instance"));
        paramMethodVisitor.visitVarInsn(21, paramContext.var(paramFieldInfo.getName() + "_asm"));
        _set(paramContext, paramMethodVisitor, paramFieldInfo);
        return;
      }
      if (localClass != Long.TYPE)
        break label314;
      paramMethodVisitor.visitVarInsn(25, paramContext.var("instance"));
      paramMethodVisitor.visitVarInsn(22, paramContext.var(paramFieldInfo.getName() + "_asm", 2));
      if (paramFieldInfo.getMethod() == null)
        break;
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(paramContext.getClazz()), paramFieldInfo.getMethod().getName(), ASMUtils.getDesc(paramFieldInfo.getMethod()));
    }
    while (paramFieldInfo.getMethod().getReturnType().equals(Void.TYPE));
    paramMethodVisitor.visitInsn(87);
    return;
    paramMethodVisitor.visitFieldInsn(181, ASMUtils.getType(paramFieldInfo.getDeclaringClass()), paramFieldInfo.getField().getName(), ASMUtils.getDesc(paramFieldInfo.getFieldClass()));
    return;
    label314: if (localClass == Float.TYPE)
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("instance"));
      paramMethodVisitor.visitVarInsn(23, paramContext.var(paramFieldInfo.getName() + "_asm"));
      _set(paramContext, paramMethodVisitor, paramFieldInfo);
      return;
    }
    if (localClass == Double.TYPE)
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("instance"));
      paramMethodVisitor.visitVarInsn(24, paramContext.var(paramFieldInfo.getName() + "_asm", 2));
      _set(paramContext, paramMethodVisitor, paramFieldInfo);
      return;
    }
    if (localClass == String.class)
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("instance"));
      paramMethodVisitor.visitVarInsn(25, paramContext.var(paramFieldInfo.getName() + "_asm"));
      _set(paramContext, paramMethodVisitor, paramFieldInfo);
      return;
    }
    if (localClass.isEnum())
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("instance"));
      paramMethodVisitor.visitVarInsn(25, paramContext.var(paramFieldInfo.getName() + "_asm"));
      _set(paramContext, paramMethodVisitor, paramFieldInfo);
      return;
    }
    if (Collection.class.isAssignableFrom(localClass))
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("instance"));
      if (getCollectionItemClass(localType) == String.class)
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var(paramFieldInfo.getName() + "_asm"));
        paramMethodVisitor.visitTypeInsn(192, ASMUtils.getType(localClass));
      }
      while (true)
      {
        _set(paramContext, paramMethodVisitor, paramFieldInfo);
        return;
        paramMethodVisitor.visitVarInsn(25, paramContext.var(paramFieldInfo.getName() + "_asm"));
      }
    }
    paramMethodVisitor.visitVarInsn(25, paramContext.var("instance"));
    paramMethodVisitor.visitVarInsn(25, paramContext.var(paramFieldInfo.getName() + "_asm"));
    _set(paramContext, paramMethodVisitor, paramFieldInfo);
  }

  private void _newCollection(MethodVisitor paramMethodVisitor, Class<?> paramClass)
  {
    if (paramClass.isAssignableFrom(ArrayList.class))
    {
      paramMethodVisitor.visitTypeInsn(187, ASMUtils.getType(ArrayList.class));
      paramMethodVisitor.visitInsn(89);
      paramMethodVisitor.visitMethodInsn(183, ASMUtils.getType(ArrayList.class), "<init>", "()V");
    }
    while (true)
    {
      paramMethodVisitor.visitTypeInsn(192, ASMUtils.getType(paramClass));
      return;
      if (paramClass.isAssignableFrom(LinkedList.class))
      {
        paramMethodVisitor.visitTypeInsn(187, ASMUtils.getType(LinkedList.class));
        paramMethodVisitor.visitInsn(89);
        paramMethodVisitor.visitMethodInsn(183, ASMUtils.getType(LinkedList.class), "<init>", "()V");
      }
      else if (paramClass.isAssignableFrom(HashSet.class))
      {
        paramMethodVisitor.visitTypeInsn(187, ASMUtils.getType(HashSet.class));
        paramMethodVisitor.visitInsn(89);
        paramMethodVisitor.visitMethodInsn(183, ASMUtils.getType(HashSet.class), "<init>", "()V");
      }
      else if (paramClass.isAssignableFrom(TreeSet.class))
      {
        paramMethodVisitor.visitTypeInsn(187, ASMUtils.getType(TreeSet.class));
        paramMethodVisitor.visitInsn(89);
        paramMethodVisitor.visitMethodInsn(183, ASMUtils.getType(TreeSet.class), "<init>", "()V");
      }
      else
      {
        paramMethodVisitor.visitTypeInsn(187, ASMUtils.getType(paramClass));
        paramMethodVisitor.visitInsn(89);
        paramMethodVisitor.visitMethodInsn(183, ASMUtils.getType(paramClass), "<init>", "()V");
      }
    }
  }

  private void _set(Context paramContext, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo)
  {
    if (paramFieldInfo.getMethod() != null)
    {
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(paramFieldInfo.getDeclaringClass()), paramFieldInfo.getMethod().getName(), ASMUtils.getDesc(paramFieldInfo.getMethod()));
      if (!paramFieldInfo.getMethod().getReturnType().equals(Void.TYPE))
        paramMethodVisitor.visitInsn(87);
      return;
    }
    paramMethodVisitor.visitFieldInsn(181, ASMUtils.getType(paramFieldInfo.getDeclaringClass()), paramFieldInfo.getField().getName(), ASMUtils.getDesc(paramFieldInfo.getFieldClass()));
  }

  private void _setContext(Context paramContext, MethodVisitor paramMethodVisitor)
  {
    paramMethodVisitor.visitVarInsn(25, 1);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("context"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "setContext", "(" + ASMUtils.getDesc(ParseContext.class) + ")V");
    Label localLabel = new Label();
    paramMethodVisitor.visitVarInsn(25, paramContext.var("childContext"));
    paramMethodVisitor.visitJumpInsn(198, localLabel);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("childContext"));
    paramMethodVisitor.visitVarInsn(25, paramContext.var("instance"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(ParseContext.class), "setObject", "(Ljava/lang/Object;)V");
    paramMethodVisitor.visitLabel(localLabel);
  }

  private void defineVarLexer(Context paramContext, MethodVisitor paramMethodVisitor)
  {
    paramMethodVisitor.visitVarInsn(25, 1);
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "getLexer", "()" + ASMUtils.getDesc(JSONLexer.class));
    paramMethodVisitor.visitTypeInsn(192, ASMUtils.getType(JSONLexerBase.class));
    paramMethodVisitor.visitVarInsn(58, paramContext.var("lexer"));
  }

  private Class<?> getCollectionItemClass(java.lang.reflect.Type paramType)
  {
    Object localObject;
    if ((paramType instanceof ParameterizedType))
    {
      java.lang.reflect.Type localType = ((ParameterizedType)paramType).getActualTypeArguments()[0];
      if ((localType instanceof Class))
      {
        localObject = (Class)localType;
        if (!Modifier.isPublic(((Class)localObject).getModifiers()))
          throw new ASMException("can not create ASMParser");
      }
      else
      {
        throw new ASMException("can not create ASMParser");
      }
    }
    else
    {
      localObject = Object.class;
    }
    return localObject;
  }

  public static final ASMDeserializerFactory getInstance()
  {
    return instance;
  }

  void _deserialze(ClassWriter paramClassWriter, Context paramContext)
  {
    if (paramContext.getFieldInfoList().size() == 0)
      return;
    Iterator localIterator = paramContext.getFieldInfoList().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        break label105;
      FieldInfo localFieldInfo3 = (FieldInfo)localIterator.next();
      Class localClass4 = localFieldInfo3.getFieldClass();
      java.lang.reflect.Type localType2 = localFieldInfo3.getFieldType();
      if (localClass4 == Character.TYPE)
        break;
      if (Collection.class.isAssignableFrom(localClass4))
        if ((!(localType2 instanceof ParameterizedType)) || (!(((ParameterizedType)localType2).getActualTypeArguments()[0] instanceof Class)))
          break;
    }
    label105: Collections.sort(paramContext.getFieldInfoList());
    MethodVisitor localMethodVisitor = paramClassWriter.visitMethod(1, "deserialze", "(" + ASMUtils.getDesc(DefaultJSONParser.class) + ASMUtils.getDesc(java.lang.reflect.Type.class) + "Ljava/lang/Object;)Ljava/lang/Object;", null, null);
    Label localLabel1 = new Label();
    Label localLabel2 = new Label();
    Label localLabel3 = new Label();
    Label localLabel4 = new Label();
    defineVarLexer(paramContext, localMethodVisitor);
    _isEnable(paramContext, localMethodVisitor, Feature.SortFeidFastMatch);
    localMethodVisitor.visitJumpInsn(153, localLabel2);
    Label localLabel5 = new Label();
    localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
    localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "token", "()I");
    localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "LBRACKET", "I");
    localMethodVisitor.visitJumpInsn(160, localLabel5);
    localMethodVisitor.visitVarInsn(25, 0);
    localMethodVisitor.visitVarInsn(25, 1);
    localMethodVisitor.visitVarInsn(25, 2);
    localMethodVisitor.visitVarInsn(25, 3);
    localMethodVisitor.visitMethodInsn(183, paramContext.getClassName(), "deserialzeArrayMapping", "(" + ASMUtils.getDesc(DefaultJSONParser.class) + ASMUtils.getDesc(java.lang.reflect.Type.class) + "Ljava/lang/Object;)Ljava/lang/Object;");
    localMethodVisitor.visitInsn(176);
    localMethodVisitor.visitLabel(localLabel5);
    localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
    localMethodVisitor.visitLdcInsn(paramContext.getClazz().getName());
    localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanType", "(Ljava/lang/String;)I");
    localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONLexerBase.class), "NOT_MATCH", "I");
    localMethodVisitor.visitJumpInsn(159, localLabel2);
    localMethodVisitor.visitVarInsn(25, 1);
    localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "getContext", "()Lcom/alibaba/fastjson/parser/ParseContext;");
    localMethodVisitor.visitVarInsn(58, paramContext.var("mark_context"));
    localMethodVisitor.visitInsn(3);
    localMethodVisitor.visitVarInsn(54, paramContext.var("matchedCount"));
    _createInstance(paramContext, localMethodVisitor);
    localMethodVisitor.visitVarInsn(25, 1);
    localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "getContext", "()" + ASMUtils.getDesc(ParseContext.class));
    localMethodVisitor.visitVarInsn(58, paramContext.var("context"));
    localMethodVisitor.visitVarInsn(25, 1);
    localMethodVisitor.visitVarInsn(25, paramContext.var("context"));
    localMethodVisitor.visitVarInsn(25, paramContext.var("instance"));
    localMethodVisitor.visitVarInsn(25, 3);
    localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "setContext", "(" + ASMUtils.getDesc(ParseContext.class) + "Ljava/lang/Object;Ljava/lang/Object;)" + ASMUtils.getDesc(ParseContext.class));
    localMethodVisitor.visitVarInsn(58, paramContext.var("childContext"));
    localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
    localMethodVisitor.visitFieldInsn(180, ASMUtils.getType(JSONLexerBase.class), "matchStat", "I");
    localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONLexerBase.class), "END", "I");
    localMethodVisitor.visitJumpInsn(159, localLabel3);
    localMethodVisitor.visitInsn(3);
    localMethodVisitor.visitIntInsn(54, paramContext.var("matchStat"));
    int i = paramContext.getFieldInfoList().size();
    for (int j = 0; j < i; j += 32)
    {
      localMethodVisitor.visitInsn(3);
      localMethodVisitor.visitVarInsn(54, paramContext.var("_asm_flag_" + j / 32));
    }
    int k = 0;
    if (k < i)
    {
      FieldInfo localFieldInfo2 = (FieldInfo)paramContext.getFieldInfoList().get(k);
      Class localClass3 = localFieldInfo2.getFieldClass();
      if ((localClass3 == Boolean.TYPE) || (localClass3 == Byte.TYPE) || (localClass3 == Short.TYPE) || (localClass3 == Integer.TYPE))
      {
        localMethodVisitor.visitInsn(3);
        localMethodVisitor.visitVarInsn(54, paramContext.var(localFieldInfo2.getName() + "_asm"));
      }
      while (true)
      {
        k++;
        break;
        if (localClass3 == Long.TYPE)
        {
          localMethodVisitor.visitInsn(9);
          localMethodVisitor.visitVarInsn(55, paramContext.var(localFieldInfo2.getName() + "_asm", 2));
        }
        else if (localClass3 == Float.TYPE)
        {
          localMethodVisitor.visitInsn(11);
          localMethodVisitor.visitVarInsn(56, paramContext.var(localFieldInfo2.getName() + "_asm"));
        }
        else
        {
          if (localClass3 != Double.TYPE)
            break label1210;
          localMethodVisitor.visitInsn(14);
          localMethodVisitor.visitVarInsn(57, paramContext.var(localFieldInfo2.getName() + "_asm", 2));
        }
      }
      label1210: if (localClass3 == String.class)
      {
        Label localLabel10 = new Label();
        _isEnable(paramContext, localMethodVisitor, Feature.InitStringFieldAsEmpty);
        localMethodVisitor.visitJumpInsn(153, localLabel10);
        _setFlag(localMethodVisitor, paramContext, k);
        localMethodVisitor.visitLabel(localLabel10);
        localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
        localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "stringDefaultValue", "()Ljava/lang/String;");
      }
      while (true)
      {
        localMethodVisitor.visitTypeInsn(192, ASMUtils.getType(localClass3));
        localMethodVisitor.visitVarInsn(58, paramContext.var(localFieldInfo2.getName() + "_asm"));
        break;
        localMethodVisitor.visitInsn(1);
      }
    }
    int m = 0;
    if (m < i)
    {
      FieldInfo localFieldInfo1 = (FieldInfo)paramContext.getFieldInfoList().get(m);
      Class localClass1 = localFieldInfo1.getFieldClass();
      java.lang.reflect.Type localType1 = localFieldInfo1.getFieldType();
      Label localLabel6 = new Label();
      if (localClass1 == Boolean.TYPE)
      {
        localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
        localMethodVisitor.visitVarInsn(25, 0);
        localMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), localFieldInfo1.getName() + "_asm_prefix__", "[C");
        localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanFieldBoolean", "([C)Z");
        localMethodVisitor.visitVarInsn(54, paramContext.var(localFieldInfo1.getName() + "_asm"));
        label1547: localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
        localMethodVisitor.visitFieldInsn(180, ASMUtils.getType(JSONLexerBase.class), "matchStat", "I");
        Label localLabel8 = new Label();
        localMethodVisitor.visitJumpInsn(158, localLabel8);
        _setFlag(localMethodVisitor, paramContext, m);
        localMethodVisitor.visitLabel(localLabel8);
        localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
        localMethodVisitor.visitFieldInsn(180, ASMUtils.getType(JSONLexerBase.class), "matchStat", "I");
        localMethodVisitor.visitInsn(89);
        localMethodVisitor.visitVarInsn(54, paramContext.var("matchStat"));
        localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONLexerBase.class), "NOT_MATCH", "I");
        localMethodVisitor.visitJumpInsn(159, localLabel1);
        localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
        localMethodVisitor.visitFieldInsn(180, ASMUtils.getType(JSONLexerBase.class), "matchStat", "I");
        localMethodVisitor.visitJumpInsn(158, localLabel6);
        localMethodVisitor.visitVarInsn(21, paramContext.var("matchedCount"));
        localMethodVisitor.visitInsn(4);
        localMethodVisitor.visitInsn(96);
        localMethodVisitor.visitVarInsn(54, paramContext.var("matchedCount"));
        localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
        localMethodVisitor.visitFieldInsn(180, ASMUtils.getType(JSONLexerBase.class), "matchStat", "I");
        localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONLexerBase.class), "END", "I");
        localMethodVisitor.visitJumpInsn(159, localLabel4);
        localMethodVisitor.visitLabel(localLabel6);
        if (m == i - 1)
        {
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitFieldInsn(180, ASMUtils.getType(JSONLexerBase.class), "matchStat", "I");
          localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONLexerBase.class), "END", "I");
          localMethodVisitor.visitJumpInsn(160, localLabel1);
        }
      }
      while (true)
      {
        m++;
        break;
        if (localClass1 == Byte.TYPE)
        {
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitVarInsn(25, 0);
          localMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), localFieldInfo1.getName() + "_asm_prefix__", "[C");
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanFieldInt", "([C)I");
          localMethodVisitor.visitVarInsn(54, paramContext.var(localFieldInfo1.getName() + "_asm"));
          break label1547;
        }
        if (localClass1 == Short.TYPE)
        {
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitVarInsn(25, 0);
          localMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), localFieldInfo1.getName() + "_asm_prefix__", "[C");
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanFieldInt", "([C)I");
          localMethodVisitor.visitVarInsn(54, paramContext.var(localFieldInfo1.getName() + "_asm"));
          break label1547;
        }
        if (localClass1 == Integer.TYPE)
        {
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitVarInsn(25, 0);
          localMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), localFieldInfo1.getName() + "_asm_prefix__", "[C");
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanFieldInt", "([C)I");
          localMethodVisitor.visitVarInsn(54, paramContext.var(localFieldInfo1.getName() + "_asm"));
          break label1547;
        }
        if (localClass1 == Long.TYPE)
        {
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitVarInsn(25, 0);
          localMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), localFieldInfo1.getName() + "_asm_prefix__", "[C");
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanFieldLong", "([C)J");
          localMethodVisitor.visitVarInsn(55, paramContext.var(localFieldInfo1.getName() + "_asm", 2));
          break label1547;
        }
        if (localClass1 == Float.TYPE)
        {
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitVarInsn(25, 0);
          localMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), localFieldInfo1.getName() + "_asm_prefix__", "[C");
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanFieldFloat", "([C)F");
          localMethodVisitor.visitVarInsn(56, paramContext.var(localFieldInfo1.getName() + "_asm"));
          break label1547;
        }
        if (localClass1 == Double.TYPE)
        {
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitVarInsn(25, 0);
          localMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), localFieldInfo1.getName() + "_asm_prefix__", "[C");
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanFieldDouble", "([C)D");
          localMethodVisitor.visitVarInsn(57, paramContext.var(localFieldInfo1.getName() + "_asm", 2));
          break label1547;
        }
        if (localClass1 == String.class)
        {
          Label localLabel7 = new Label();
          localMethodVisitor.visitIntInsn(21, paramContext.var("matchStat"));
          localMethodVisitor.visitInsn(7);
          localMethodVisitor.visitJumpInsn(160, localLabel7);
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "stringDefaultValue", "()Ljava/lang/String;");
          localMethodVisitor.visitVarInsn(58, paramContext.var(localFieldInfo1.getName() + "_asm"));
          localMethodVisitor.visitJumpInsn(167, localLabel6);
          localMethodVisitor.visitLabel(localLabel7);
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitVarInsn(25, 0);
          localMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), localFieldInfo1.getName() + "_asm_prefix__", "[C");
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanFieldString", "([C)Ljava/lang/String;");
          localMethodVisitor.visitVarInsn(58, paramContext.var(localFieldInfo1.getName() + "_asm"));
          break label1547;
        }
        if (localClass1.isEnum())
        {
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitVarInsn(25, 0);
          localMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), localFieldInfo1.getName() + "_asm_prefix__", "[C");
          Label localLabel9 = new Label();
          localMethodVisitor.visitInsn(1);
          localMethodVisitor.visitTypeInsn(192, ASMUtils.getType(localClass1));
          localMethodVisitor.visitVarInsn(58, paramContext.var(localFieldInfo1.getName() + "_asm"));
          localMethodVisitor.visitVarInsn(25, 1);
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "getSymbolTable", "()" + ASMUtils.getDesc(SymbolTable.class));
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanFieldSymbol", "([C" + ASMUtils.getDesc(SymbolTable.class) + ")Ljava/lang/String;");
          localMethodVisitor.visitInsn(89);
          localMethodVisitor.visitVarInsn(58, paramContext.var(localFieldInfo1.getName() + "_asm_enumName"));
          localMethodVisitor.visitJumpInsn(198, localLabel9);
          localMethodVisitor.visitVarInsn(25, paramContext.var(localFieldInfo1.getName() + "_asm_enumName"));
          localMethodVisitor.visitMethodInsn(184, ASMUtils.getType(localClass1), "valueOf", "(Ljava/lang/String;)" + ASMUtils.getDesc(localClass1));
          localMethodVisitor.visitVarInsn(58, paramContext.var(localFieldInfo1.getName() + "_asm"));
          localMethodVisitor.visitLabel(localLabel9);
          break label1547;
        }
        if (Collection.class.isAssignableFrom(localClass1))
        {
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitVarInsn(25, 0);
          localMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), localFieldInfo1.getName() + "_asm_prefix__", "[C");
          Class localClass2 = getCollectionItemClass(localType1);
          if (localClass2 == String.class)
          {
            localMethodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(localClass1)));
            localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanFieldStringArray", "([CLjava/lang/Class;)" + ASMUtils.getDesc(Collection.class));
            localMethodVisitor.visitVarInsn(58, paramContext.var(localFieldInfo1.getName() + "_asm"));
            break label1547;
          }
          _deserialze_list_obj(paramContext, localMethodVisitor, localLabel1, localFieldInfo1, localClass1, localClass2, m);
          if (m != i - 1)
            continue;
          _deserialize_endCheck(paramContext, localMethodVisitor, localLabel1);
          continue;
        }
        _deserialze_obj(paramContext, localMethodVisitor, localLabel1, localFieldInfo1, localClass1, m);
        if (m == i - 1)
          _deserialize_endCheck(paramContext, localMethodVisitor, localLabel1);
      }
    }
    localMethodVisitor.visitLabel(localLabel4);
    if ((!paramContext.getClazz().isInterface()) && (!Modifier.isAbstract(paramContext.getClazz().getModifiers())))
      _batchSet(paramContext, localMethodVisitor);
    localMethodVisitor.visitLabel(localLabel3);
    _setContext(paramContext, localMethodVisitor);
    localMethodVisitor.visitVarInsn(25, paramContext.var("instance"));
    localMethodVisitor.visitInsn(176);
    localMethodVisitor.visitLabel(localLabel1);
    _batchSet(paramContext, localMethodVisitor);
    localMethodVisitor.visitVarInsn(25, 0);
    localMethodVisitor.visitVarInsn(25, 1);
    localMethodVisitor.visitVarInsn(25, 2);
    localMethodVisitor.visitVarInsn(25, 3);
    localMethodVisitor.visitVarInsn(25, paramContext.var("instance"));
    localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(ASMJavaBeanDeserializer.class), "parseRest", "(" + ASMUtils.getDesc(DefaultJSONParser.class) + ASMUtils.getDesc(java.lang.reflect.Type.class) + "Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
    localMethodVisitor.visitTypeInsn(192, ASMUtils.getType(paramContext.getClazz()));
    localMethodVisitor.visitInsn(176);
    localMethodVisitor.visitLabel(localLabel2);
    localMethodVisitor.visitVarInsn(25, 0);
    localMethodVisitor.visitVarInsn(25, 1);
    localMethodVisitor.visitVarInsn(25, 2);
    localMethodVisitor.visitVarInsn(25, 3);
    localMethodVisitor.visitMethodInsn(183, ASMUtils.getType(ASMJavaBeanDeserializer.class), "deserialze", "(" + ASMUtils.getDesc(DefaultJSONParser.class) + ASMUtils.getDesc(java.lang.reflect.Type.class) + "Ljava/lang/Object;)Ljava/lang/Object;");
    localMethodVisitor.visitInsn(176);
    localMethodVisitor.visitMaxs(5, paramContext.getVariantCount());
    localMethodVisitor.visitEnd();
  }

  void _deserialzeArrayMapping(ClassWriter paramClassWriter, Context paramContext)
  {
    MethodVisitor localMethodVisitor = paramClassWriter.visitMethod(1, "deserialzeArrayMapping", "(" + ASMUtils.getDesc(DefaultJSONParser.class) + ASMUtils.getDesc(java.lang.reflect.Type.class) + "Ljava/lang/Object;)Ljava/lang/Object;", null, null);
    defineVarLexer(paramContext, localMethodVisitor);
    _createInstance(paramContext, localMethodVisitor);
    List localList = paramContext.getBeanInfo().getSortedFieldList();
    int i = localList.size();
    int j = 0;
    if (j < i)
    {
      int k;
      label99: int m;
      label108: FieldInfo localFieldInfo;
      Class localClass1;
      java.lang.reflect.Type localType;
      if (j == i - 1)
      {
        k = 1;
        if (k == 0)
          break label251;
        m = 93;
        localFieldInfo = (FieldInfo)localList.get(j);
        localClass1 = localFieldInfo.getFieldClass();
        localType = localFieldInfo.getFieldType();
        if ((localClass1 != Byte.TYPE) && (localClass1 != Short.TYPE) && (localClass1 != Integer.TYPE))
          break label258;
        localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
        localMethodVisitor.visitVarInsn(16, m);
        localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanInt", "(C)I");
        localMethodVisitor.visitVarInsn(54, paramContext.var(localFieldInfo.getName() + "_asm"));
      }
      label251: Class localClass2;
      while (true)
      {
        j++;
        break;
        k = 0;
        break label99;
        m = 44;
        break label108;
        label258: if (localClass1 == Long.TYPE)
        {
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitVarInsn(16, m);
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanLong", "(C)J");
          localMethodVisitor.visitVarInsn(55, paramContext.var(localFieldInfo.getName() + "_asm", 2));
        }
        else if (localClass1 == Boolean.TYPE)
        {
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitVarInsn(16, m);
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanBoolean", "(C)Z");
          localMethodVisitor.visitVarInsn(54, paramContext.var(localFieldInfo.getName() + "_asm"));
        }
        else if (localClass1 == Float.TYPE)
        {
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitVarInsn(16, m);
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanFloat", "(C)F");
          localMethodVisitor.visitVarInsn(56, paramContext.var(localFieldInfo.getName() + "_asm"));
        }
        else if (localClass1 == Double.TYPE)
        {
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitVarInsn(16, m);
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanDouble", "(C)D");
          localMethodVisitor.visitVarInsn(57, paramContext.var(localFieldInfo.getName() + "_asm", 2));
        }
        else if (localClass1 == Character.TYPE)
        {
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitVarInsn(16, m);
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanString", "(C)Ljava/lang/String;");
          localMethodVisitor.visitInsn(3);
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(String.class), "charAt", "(I)C");
          localMethodVisitor.visitVarInsn(54, paramContext.var(localFieldInfo.getName() + "_asm"));
        }
        else if (localClass1 == String.class)
        {
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitVarInsn(16, m);
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanString", "(C)Ljava/lang/String;");
          localMethodVisitor.visitVarInsn(58, paramContext.var(localFieldInfo.getName() + "_asm"));
        }
        else if (localClass1.isEnum())
        {
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(localClass1)));
          localMethodVisitor.visitVarInsn(25, 1);
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "getSymbolTable", "()" + ASMUtils.getDesc(SymbolTable.class));
          localMethodVisitor.visitVarInsn(16, m);
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanEnum", "(Ljava/lang/Class;" + ASMUtils.getDesc(SymbolTable.class) + "C)Ljava/lang/Enum;");
          localMethodVisitor.visitTypeInsn(192, ASMUtils.getType(localClass1));
          localMethodVisitor.visitVarInsn(58, paramContext.var(localFieldInfo.getName() + "_asm"));
        }
        else
        {
          if (!Collection.class.isAssignableFrom(localClass1))
            break label1389;
          localClass2 = getCollectionItemClass(localType);
          if (localClass2 != String.class)
            break label1148;
          localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
          localMethodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(localClass1)));
          localMethodVisitor.visitVarInsn(16, m);
          localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "scanStringArray", "(Ljava/lang/Class;C)Ljava/util/Collection;");
          localMethodVisitor.visitVarInsn(58, paramContext.var(localFieldInfo.getName() + "_asm"));
        }
      }
      label1148: localMethodVisitor.visitVarInsn(25, 1);
      if (j == 0)
        localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "LBRACKET", "I");
      while (true)
      {
        localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "LBRACKET", "I");
        localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "accept", "(II)V");
        _newCollection(localMethodVisitor, localClass1);
        localMethodVisitor.visitInsn(89);
        localMethodVisitor.visitVarInsn(58, paramContext.var(localFieldInfo.getName() + "_asm"));
        _getCollectionFieldItemDeser(paramContext, localMethodVisitor, localFieldInfo, localClass2);
        localMethodVisitor.visitVarInsn(25, 1);
        localMethodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(localClass2)));
        localMethodVisitor.visitVarInsn(25, 3);
        localMethodVisitor.visitMethodInsn(184, ASMUtils.getType(ASMUtils.class), "parseArray", "(Ljava/util/Collection;" + ASMUtils.getDesc(ObjectDeserializer.class) + ASMUtils.getDesc(DefaultJSONParser.class) + "Ljava/lang/reflect/Type;Ljava/lang/Object;)V");
        break;
        localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "COMMA", "I");
      }
      label1389: localMethodVisitor.visitVarInsn(25, 1);
      if (j == 0)
      {
        localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "LBRACKET", "I");
        label1421: localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "LBRACKET", "I");
        localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "accept", "(II)V");
        _deserObject(paramContext, localMethodVisitor, localFieldInfo, localClass1);
        localMethodVisitor.visitVarInsn(25, 1);
        if (k != 0)
          break label1563;
        localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "COMMA", "I");
        localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "LBRACKET", "I");
      }
      while (true)
      {
        localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(DefaultJSONParser.class), "accept", "(II)V");
        break;
        localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "COMMA", "I");
        break label1421;
        label1563: localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "RBRACKET", "I");
        localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "EOF", "I");
      }
    }
    _batchSet(paramContext, localMethodVisitor, false);
    localMethodVisitor.visitVarInsn(25, paramContext.var("lexer"));
    localMethodVisitor.visitFieldInsn(178, ASMUtils.getType(JSONToken.class), "COMMA", "I");
    localMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONLexerBase.class), "nextToken", "(I)V");
    localMethodVisitor.visitVarInsn(25, paramContext.var("instance"));
    localMethodVisitor.visitInsn(176);
    localMethodVisitor.visitMaxs(5, paramContext.getVariantCount());
    localMethodVisitor.visitEnd();
  }

  void _isFlag(MethodVisitor paramMethodVisitor, Context paramContext, int paramInt, Label paramLabel)
  {
    paramMethodVisitor.visitVarInsn(21, paramContext.var("_asm_flag_" + paramInt / 32));
    paramMethodVisitor.visitLdcInsn(Integer.valueOf(1 << paramInt));
    paramMethodVisitor.visitInsn(126);
    paramMethodVisitor.visitJumpInsn(153, paramLabel);
  }

  void _setFlag(MethodVisitor paramMethodVisitor, Context paramContext, int paramInt)
  {
    String str = "_asm_flag_" + paramInt / 32;
    paramMethodVisitor.visitVarInsn(21, paramContext.var(str));
    paramMethodVisitor.visitLdcInsn(Integer.valueOf(1 << paramInt));
    paramMethodVisitor.visitInsn(128);
    paramMethodVisitor.visitVarInsn(54, paramContext.var(str));
  }

  public FieldDeserializer createFieldDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, FieldInfo paramFieldInfo)
    throws Exception
  {
    Class localClass = paramFieldInfo.getFieldClass();
    if ((localClass == Integer.TYPE) || (localClass == Long.TYPE) || (localClass == String.class))
      return createStringFieldDeserializer(paramParserConfig, paramClass, paramFieldInfo);
    return paramParserConfig.createFieldDeserializerWithoutASM(paramParserConfig, paramClass, paramFieldInfo);
  }

  public ObjectDeserializer createJavaBeanDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, java.lang.reflect.Type paramType)
    throws Exception
  {
    if (paramClass.isPrimitive())
      throw new IllegalArgumentException("not support type :" + paramClass.getName());
    String str = getGenClassName(paramClass);
    ClassWriter localClassWriter = new ClassWriter();
    localClassWriter.visit(49, 33, str, ASMUtils.getType(ASMJavaBeanDeserializer.class), null);
    DeserializeBeanInfo localDeserializeBeanInfo = DeserializeBeanInfo.computeSetters(paramClass, paramType);
    _init(localClassWriter, new Context(str, paramParserConfig, localDeserializeBeanInfo, 3));
    _createInstance(localClassWriter, new Context(str, paramParserConfig, localDeserializeBeanInfo, 3));
    _deserialze(localClassWriter, new Context(str, paramParserConfig, localDeserializeBeanInfo, 4));
    _deserialzeArrayMapping(localClassWriter, new Context(str, paramParserConfig, localDeserializeBeanInfo, 4));
    byte[] arrayOfByte = localClassWriter.toByteArray();
    return (ObjectDeserializer)this.classLoader.defineClassPublic(str, arrayOfByte, 0, arrayOfByte.length).getConstructor(new Class[] { ParserConfig.class, Class.class }).newInstance(new Object[] { paramParserConfig, paramClass });
  }

  public FieldDeserializer createStringFieldDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, FieldInfo paramFieldInfo)
    throws Exception
  {
    Class localClass = paramFieldInfo.getFieldClass();
    Method localMethod = paramFieldInfo.getMethod();
    String str = getGenFieldDeserializer(paramClass, paramFieldInfo);
    ClassWriter localClassWriter = new ClassWriter();
    Object localObject;
    int i;
    if (localClass == Integer.TYPE)
    {
      localObject = IntegerFieldDeserializer.class;
      if (!paramClass.isInterface())
        break label497;
      i = 185;
      label54: localClassWriter.visit(49, 33, str, ASMUtils.getType((Class)localObject), null);
      MethodVisitor localMethodVisitor1 = localClassWriter.visitMethod(1, "<init>", "(" + ASMUtils.getDesc(ParserConfig.class) + ASMUtils.getDesc(Class.class) + ASMUtils.getDesc(FieldInfo.class) + ")V", null, null);
      localMethodVisitor1.visitVarInsn(25, 0);
      localMethodVisitor1.visitVarInsn(25, 1);
      localMethodVisitor1.visitVarInsn(25, 2);
      localMethodVisitor1.visitVarInsn(25, 3);
      localMethodVisitor1.visitMethodInsn(183, ASMUtils.getType((Class)localObject), "<init>", "(" + ASMUtils.getDesc(ParserConfig.class) + ASMUtils.getDesc(Class.class) + ASMUtils.getDesc(FieldInfo.class) + ")V");
      localMethodVisitor1.visitInsn(177);
      localMethodVisitor1.visitMaxs(4, 6);
      localMethodVisitor1.visitEnd();
      if (localMethod != null)
      {
        if (localClass != Integer.TYPE)
          break label505;
        MethodVisitor localMethodVisitor4 = localClassWriter.visitMethod(1, "setValue", "(" + ASMUtils.getDesc(Object.class) + "I)V", null, null);
        localMethodVisitor4.visitVarInsn(25, 1);
        localMethodVisitor4.visitTypeInsn(192, ASMUtils.getType(localMethod.getDeclaringClass()));
        localMethodVisitor4.visitVarInsn(21, 2);
        localMethodVisitor4.visitMethodInsn(i, ASMUtils.getType(localMethod.getDeclaringClass()), localMethod.getName(), ASMUtils.getDesc(localMethod));
        localMethodVisitor4.visitInsn(177);
        localMethodVisitor4.visitMaxs(3, 3);
        localMethodVisitor4.visitEnd();
      }
    }
    while (true)
    {
      byte[] arrayOfByte = localClassWriter.toByteArray();
      return (FieldDeserializer)this.classLoader.defineClassPublic(str, arrayOfByte, 0, arrayOfByte.length).getConstructor(new Class[] { ParserConfig.class, Class.class, FieldInfo.class }).newInstance(new Object[] { paramParserConfig, paramClass, paramFieldInfo });
      if (localClass == Long.TYPE)
      {
        localObject = LongFieldDeserializer.class;
        break;
      }
      localObject = StringFieldDeserializer.class;
      break;
      label497: i = 182;
      break label54;
      label505: if (localClass == Long.TYPE)
      {
        MethodVisitor localMethodVisitor3 = localClassWriter.visitMethod(1, "setValue", "(" + ASMUtils.getDesc(Object.class) + "J)V", null, null);
        localMethodVisitor3.visitVarInsn(25, 1);
        localMethodVisitor3.visitTypeInsn(192, ASMUtils.getType(localMethod.getDeclaringClass()));
        localMethodVisitor3.visitVarInsn(22, 2);
        localMethodVisitor3.visitMethodInsn(i, ASMUtils.getType(localMethod.getDeclaringClass()), localMethod.getName(), ASMUtils.getDesc(localMethod));
        localMethodVisitor3.visitInsn(177);
        localMethodVisitor3.visitMaxs(3, 4);
        localMethodVisitor3.visitEnd();
      }
      else
      {
        MethodVisitor localMethodVisitor2 = localClassWriter.visitMethod(1, "setValue", "(" + ASMUtils.getDesc(Object.class) + ASMUtils.getDesc(Object.class) + ")V", null, null);
        localMethodVisitor2.visitVarInsn(25, 1);
        localMethodVisitor2.visitTypeInsn(192, ASMUtils.getType(localMethod.getDeclaringClass()));
        localMethodVisitor2.visitVarInsn(25, 2);
        localMethodVisitor2.visitTypeInsn(192, ASMUtils.getType(localClass));
        localMethodVisitor2.visitMethodInsn(i, ASMUtils.getType(localMethod.getDeclaringClass()), localMethod.getName(), ASMUtils.getDesc(localMethod));
        localMethodVisitor2.visitInsn(177);
        localMethodVisitor2.visitMaxs(3, 3);
        localMethodVisitor2.visitEnd();
      }
    }
  }

  public String getGenClassName(Class<?> paramClass)
  {
    return "Fastjson_ASM_" + paramClass.getSimpleName() + "_" + this.seed.incrementAndGet();
  }

  public String getGenFieldDeserializer(Class<?> paramClass, FieldInfo paramFieldInfo)
  {
    String str = "Fastjson_ASM__Field_" + paramClass.getSimpleName();
    return str + "_" + paramFieldInfo.getName() + "_" + this.seed.incrementAndGet();
  }

  public boolean isExternalClass(Class<?> paramClass)
  {
    return this.classLoader.isExternalClass(paramClass);
  }

  static class Context
  {
    private final DeserializeBeanInfo beanInfo;
    private String className;
    private Class<?> clazz;
    private List<FieldInfo> fieldInfoList;
    private int variantIndex = 5;
    private Map<String, Integer> variants = new HashMap();

    public Context(String paramString, ParserConfig paramParserConfig, DeserializeBeanInfo paramDeserializeBeanInfo, int paramInt)
    {
      this.className = paramString;
      this.clazz = paramDeserializeBeanInfo.getClazz();
      this.variantIndex = paramInt;
      this.beanInfo = paramDeserializeBeanInfo;
      this.fieldInfoList = new ArrayList(paramDeserializeBeanInfo.getFieldList());
    }

    public DeserializeBeanInfo getBeanInfo()
    {
      return this.beanInfo;
    }

    public String getClassName()
    {
      return this.className;
    }

    public Class<?> getClazz()
    {
      return this.clazz;
    }

    public List<FieldInfo> getFieldInfoList()
    {
      return this.fieldInfoList;
    }

    public int getVariantCount()
    {
      return this.variantIndex;
    }

    public int var(String paramString)
    {
      if ((Integer)this.variants.get(paramString) == null)
      {
        Map localMap = this.variants;
        int i = this.variantIndex;
        this.variantIndex = (i + 1);
        localMap.put(paramString, Integer.valueOf(i));
      }
      return ((Integer)this.variants.get(paramString)).intValue();
    }

    public int var(String paramString, int paramInt)
    {
      if ((Integer)this.variants.get(paramString) == null)
      {
        this.variants.put(paramString, Integer.valueOf(this.variantIndex));
        this.variantIndex = (paramInt + this.variantIndex);
      }
      return ((Integer)this.variants.get(paramString)).intValue();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory
 * JD-Core Version:    0.6.2
 */