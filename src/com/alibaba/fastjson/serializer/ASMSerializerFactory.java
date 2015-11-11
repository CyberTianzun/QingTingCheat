package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.FieldVisitor;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ASMSerializerFactory
  implements Opcodes
{
  private ASMClassLoader classLoader = new ASMClassLoader();
  private final AtomicLong seed = new AtomicLong();

  private void _after(MethodVisitor paramMethodVisitor, Context paramContext)
  {
    paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
    paramMethodVisitor.visitVarInsn(25, paramContext.obj());
    paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
    paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "writeAfter", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;C)C");
    paramMethodVisitor.visitVarInsn(54, paramContext.var("seperator"));
  }

  private void _apply(MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    Class localClass = paramFieldInfo.getFieldClass();
    paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
    paramMethodVisitor.visitVarInsn(25, paramContext.obj());
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    if (localClass == Byte.TYPE)
    {
      paramMethodVisitor.visitVarInsn(21, paramContext.var("byte"));
      paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "apply", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;B)Z");
      return;
    }
    if (localClass == Short.TYPE)
    {
      paramMethodVisitor.visitVarInsn(21, paramContext.var("short"));
      paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "apply", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;S)Z");
      return;
    }
    if (localClass == Integer.TYPE)
    {
      paramMethodVisitor.visitVarInsn(21, paramContext.var("int"));
      paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "apply", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;I)Z");
      return;
    }
    if (localClass == Character.TYPE)
    {
      paramMethodVisitor.visitVarInsn(21, paramContext.var("char"));
      paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "apply", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;C)Z");
      return;
    }
    if (localClass == Long.TYPE)
    {
      paramMethodVisitor.visitVarInsn(22, paramContext.var("long", 2));
      paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "apply", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;J)Z");
      return;
    }
    if (localClass == Float.TYPE)
    {
      paramMethodVisitor.visitVarInsn(23, paramContext.var("float"));
      paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "apply", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;F)Z");
      return;
    }
    if (localClass == Double.TYPE)
    {
      paramMethodVisitor.visitVarInsn(24, paramContext.var("double", 2));
      paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "apply", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;D)Z");
      return;
    }
    if (localClass == Boolean.TYPE)
    {
      paramMethodVisitor.visitVarInsn(21, paramContext.var("boolean"));
      paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "apply", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;B)Z");
      return;
    }
    if (localClass == BigDecimal.class)
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("decimal"));
      paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "apply", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Z");
      return;
    }
    if (localClass == String.class)
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("string"));
      paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "apply", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Z");
      return;
    }
    if (localClass.isEnum())
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("enum"));
      paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "apply", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Z");
      return;
    }
    if (List.class.isAssignableFrom(localClass))
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("list"));
      paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "apply", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Z");
      return;
    }
    paramMethodVisitor.visitVarInsn(25, paramContext.var("object"));
    paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "apply", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Z");
  }

  private void _before(MethodVisitor paramMethodVisitor, Context paramContext)
  {
    paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
    paramMethodVisitor.visitVarInsn(25, paramContext.obj());
    paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
    paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "writeBefore", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;C)C");
    paramMethodVisitor.visitVarInsn(54, paramContext.var("seperator"));
  }

  private void _boolean(Class<?> paramClass, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    Label localLabel = new Label();
    _nameApply(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    _get(paramMethodVisitor, paramContext, paramFieldInfo);
    paramMethodVisitor.visitVarInsn(54, paramContext.var("boolean"));
    _filters(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    paramMethodVisitor.visitVarInsn(21, paramContext.var("boolean"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldValue", "(CLjava/lang/String;Z)V");
    _seperator(paramMethodVisitor, paramContext);
    paramMethodVisitor.visitLabel(localLabel);
  }

  private void _byte(Class<?> paramClass, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    Label localLabel = new Label();
    _nameApply(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    _get(paramMethodVisitor, paramContext, paramFieldInfo);
    paramMethodVisitor.visitVarInsn(54, paramContext.var("byte"));
    _filters(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    paramMethodVisitor.visitVarInsn(21, paramContext.var("byte"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldValue", "(CLjava/lang/String;I)V");
    _seperator(paramMethodVisitor, paramContext);
    paramMethodVisitor.visitLabel(localLabel);
  }

  private void _char(Class<?> paramClass, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    Label localLabel = new Label();
    _nameApply(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    _get(paramMethodVisitor, paramContext, paramFieldInfo);
    paramMethodVisitor.visitVarInsn(54, paramContext.var("char"));
    _filters(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    paramMethodVisitor.visitVarInsn(21, paramContext.var("char"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldValue", "(CLjava/lang/String;C)V");
    _seperator(paramMethodVisitor, paramContext);
    paramMethodVisitor.visitLabel(localLabel);
  }

  private void _decimal(Class<?> paramClass, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    Label localLabel1 = new Label();
    _nameApply(paramMethodVisitor, paramFieldInfo, paramContext, localLabel1);
    _get(paramMethodVisitor, paramContext, paramFieldInfo);
    paramMethodVisitor.visitVarInsn(58, paramContext.var("decimal"));
    _filters(paramMethodVisitor, paramFieldInfo, paramContext, localLabel1);
    Label localLabel2 = new Label();
    Label localLabel3 = new Label();
    Label localLabel4 = new Label();
    paramMethodVisitor.visitLabel(localLabel2);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("decimal"));
    paramMethodVisitor.visitJumpInsn(199, localLabel3);
    _if_write_null(paramMethodVisitor, paramFieldInfo, paramContext);
    paramMethodVisitor.visitJumpInsn(167, localLabel4);
    paramMethodVisitor.visitLabel(localLabel3);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    paramMethodVisitor.visitVarInsn(25, paramContext.var("decimal"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldValue", "(CLjava/lang/String;Ljava/math/BigDecimal;)V");
    _seperator(paramMethodVisitor, paramContext);
    paramMethodVisitor.visitJumpInsn(167, localLabel4);
    paramMethodVisitor.visitLabel(localLabel4);
    paramMethodVisitor.visitLabel(localLabel1);
  }

  private void _double(Class<?> paramClass, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    Label localLabel = new Label();
    _nameApply(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    _get(paramMethodVisitor, paramContext, paramFieldInfo);
    paramMethodVisitor.visitVarInsn(57, paramContext.var("double", 2));
    _filters(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    paramMethodVisitor.visitVarInsn(24, paramContext.var("double", 2));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldValue", "(CLjava/lang/String;D)V");
    _seperator(paramMethodVisitor, paramContext);
    paramMethodVisitor.visitLabel(localLabel);
  }

  private void _enum(Class<?> paramClass, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    JSONField localJSONField = (JSONField)paramFieldInfo.getAnnotation(JSONField.class);
    int i = 0;
    if (localJSONField != null)
    {
      SerializerFeature[] arrayOfSerializerFeature = localJSONField.serialzeFeatures();
      int j = arrayOfSerializerFeature.length;
      for (int k = 0; k < j; k++)
        if (arrayOfSerializerFeature[k] == SerializerFeature.WriteEnumUsingToString)
          i = 1;
    }
    Label localLabel1 = new Label();
    Label localLabel2 = new Label();
    Label localLabel3 = new Label();
    _nameApply(paramMethodVisitor, paramFieldInfo, paramContext, localLabel3);
    _get(paramMethodVisitor, paramContext, paramFieldInfo);
    paramMethodVisitor.visitTypeInsn(192, ASMUtils.getType(Enum.class));
    paramMethodVisitor.visitVarInsn(58, paramContext.var("enum"));
    _filters(paramMethodVisitor, paramFieldInfo, paramContext, localLabel3);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("enum"));
    paramMethodVisitor.visitJumpInsn(199, localLabel1);
    _if_write_null(paramMethodVisitor, paramFieldInfo, paramContext);
    paramMethodVisitor.visitJumpInsn(167, localLabel2);
    paramMethodVisitor.visitLabel(localLabel1);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    paramMethodVisitor.visitVarInsn(25, paramContext.var("enum"));
    if (i != 0)
    {
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(Object.class), "toString", "()Ljava/lang/String;");
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldValue", "(CLjava/lang/String;Ljava/lang/String;)V");
    }
    while (true)
    {
      _seperator(paramMethodVisitor, paramContext);
      paramMethodVisitor.visitLabel(localLabel2);
      paramMethodVisitor.visitLabel(localLabel3);
      return;
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldValue", "(CLjava/lang/String;L" + ASMUtils.getType(Enum.class) + ";)V");
    }
  }

  private void _filters(MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext, Label paramLabel)
  {
    if ((paramFieldInfo.getField() != null) && (Modifier.isTransient(paramFieldInfo.getField().getModifiers())))
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
      paramMethodVisitor.visitFieldInsn(178, ASMUtils.getType(SerializerFeature.class), "SkipTransientField", "L" + ASMUtils.getType(SerializerFeature.class) + ";");
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "isEnabled", "(L" + ASMUtils.getType(SerializerFeature.class) + ";" + ")Z");
      paramMethodVisitor.visitJumpInsn(154, paramLabel);
    }
    _apply(paramMethodVisitor, paramFieldInfo, paramContext);
    paramMethodVisitor.visitJumpInsn(153, paramLabel);
    _processKey(paramMethodVisitor, paramFieldInfo, paramContext);
    Label localLabel = new Label();
    _processValue(paramMethodVisitor, paramFieldInfo, paramContext);
    paramMethodVisitor.visitVarInsn(25, paramContext.original());
    paramMethodVisitor.visitVarInsn(25, paramContext.processValue());
    paramMethodVisitor.visitJumpInsn(165, localLabel);
    _writeObject(paramMethodVisitor, paramFieldInfo, paramContext, paramLabel);
    paramMethodVisitor.visitJumpInsn(167, paramLabel);
    paramMethodVisitor.visitLabel(localLabel);
  }

  private void _float(Class<?> paramClass, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    Label localLabel = new Label();
    _nameApply(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    _get(paramMethodVisitor, paramContext, paramFieldInfo);
    paramMethodVisitor.visitVarInsn(56, paramContext.var("float"));
    _filters(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    paramMethodVisitor.visitVarInsn(23, paramContext.var("float"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldValue", "(CLjava/lang/String;F)V");
    _seperator(paramMethodVisitor, paramContext);
    paramMethodVisitor.visitLabel(localLabel);
  }

  private void _get(MethodVisitor paramMethodVisitor, Context paramContext, FieldInfo paramFieldInfo)
  {
    Method localMethod = paramFieldInfo.getMethod();
    if (localMethod != null)
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("entity"));
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(localMethod.getDeclaringClass()), localMethod.getName(), ASMUtils.getDesc(localMethod));
      return;
    }
    paramMethodVisitor.visitVarInsn(25, paramContext.var("entity"));
    paramMethodVisitor.visitFieldInsn(180, ASMUtils.getType(paramFieldInfo.getDeclaringClass()), paramFieldInfo.getField().getName(), ASMUtils.getDesc(paramFieldInfo.getFieldClass()));
  }

  private void _if_write_null(MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    Class localClass = paramFieldInfo.getFieldClass();
    Label localLabel1 = new Label();
    Label localLabel2 = new Label();
    Label localLabel3 = new Label();
    Label localLabel4 = new Label();
    paramMethodVisitor.visitLabel(localLabel1);
    JSONField localJSONField = (JSONField)paramFieldInfo.getAnnotation(JSONField.class);
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    if (localJSONField != null)
    {
      SerializerFeature[] arrayOfSerializerFeature = localJSONField.serialzeFeatures();
      int i1 = arrayOfSerializerFeature.length;
      int i2 = 0;
      if (i2 < i1)
      {
        SerializerFeature localSerializerFeature = arrayOfSerializerFeature[i2];
        if (localSerializerFeature == SerializerFeature.WriteMapNullValue)
          i = 1;
        while (true)
        {
          i2++;
          break;
          if (localSerializerFeature == SerializerFeature.WriteNullNumberAsZero)
            m = 1;
          else if (localSerializerFeature == SerializerFeature.WriteNullStringAsEmpty)
            n = 1;
          else if (localSerializerFeature == SerializerFeature.WriteNullBooleanAsFalse)
            j = 1;
          else if (localSerializerFeature == SerializerFeature.WriteNullListAsEmpty)
            k = 1;
        }
      }
    }
    if (i == 0)
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
      paramMethodVisitor.visitFieldInsn(178, ASMUtils.getType(SerializerFeature.class), "WriteMapNullValue", "L" + ASMUtils.getType(SerializerFeature.class) + ";");
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "isEnabled", "(L" + ASMUtils.getType(SerializerFeature.class) + ";" + ")Z");
      paramMethodVisitor.visitJumpInsn(153, localLabel2);
    }
    paramMethodVisitor.visitLabel(localLabel3);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    if ((localClass == String.class) || (localClass == Character.class))
      if (n != 0)
      {
        paramMethodVisitor.visitLdcInsn("");
        paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldValue", "(CLjava/lang/String;Ljava/lang/String;)V");
      }
    while (true)
    {
      _seperator(paramMethodVisitor, paramContext);
      paramMethodVisitor.visitJumpInsn(167, localLabel4);
      paramMethodVisitor.visitLabel(localLabel2);
      paramMethodVisitor.visitLabel(localLabel4);
      return;
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldNullString", "(CLjava/lang/String;)V");
      continue;
      if (Number.class.isAssignableFrom(localClass))
      {
        if (m != 0)
        {
          paramMethodVisitor.visitInsn(3);
          paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldValue", "(CLjava/lang/String;I)V");
        }
        else
        {
          paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldNullNumber", "(CLjava/lang/String;)V");
        }
      }
      else if (localClass == Boolean.class)
      {
        if (j != 0)
        {
          paramMethodVisitor.visitInsn(3);
          paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldValue", "(CLjava/lang/String;Z)V");
        }
        else
        {
          paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldNullBoolean", "(CLjava/lang/String;)V");
        }
      }
      else if ((Collection.class.isAssignableFrom(localClass)) || (localClass.isArray()))
      {
        if (k != 0)
          paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldEmptyList", "(CLjava/lang/String;)V");
        else
          paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldNullList", "(CLjava/lang/String;)V");
      }
      else
        paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldNull", "(CLjava/lang/String;)V");
    }
  }

  private void _int(Class<?> paramClass, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    Label localLabel = new Label();
    _nameApply(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    _get(paramMethodVisitor, paramContext, paramFieldInfo);
    paramMethodVisitor.visitVarInsn(54, paramContext.var("int"));
    _filters(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    paramMethodVisitor.visitVarInsn(21, paramContext.var("int"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldValue", "(CLjava/lang/String;I)V");
    _seperator(paramMethodVisitor, paramContext);
    paramMethodVisitor.visitLabel(localLabel);
  }

  private void _list(Class<?> paramClass, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    java.lang.reflect.Type localType = paramFieldInfo.getFieldType();
    if ((localType instanceof Class));
    Class localClass;
    for (Object localObject = Object.class; ; localObject = ((java.lang.reflect.ParameterizedType)localType).getActualTypeArguments()[0])
    {
      boolean bool = localObject instanceof Class;
      localClass = null;
      if (bool)
        localClass = (Class)localObject;
      Label localLabel1 = new Label();
      Label localLabel2 = new Label();
      Label localLabel3 = new Label();
      Label localLabel4 = new Label();
      paramMethodVisitor.visitLabel(localLabel2);
      _nameApply(paramMethodVisitor, paramFieldInfo, paramContext, localLabel1);
      _get(paramMethodVisitor, paramContext, paramFieldInfo);
      paramMethodVisitor.visitTypeInsn(192, ASMUtils.getType(List.class));
      paramMethodVisitor.visitVarInsn(58, paramContext.var("list"));
      _filters(paramMethodVisitor, paramFieldInfo, paramContext, localLabel1);
      paramMethodVisitor.visitVarInsn(25, paramContext.var("list"));
      paramMethodVisitor.visitJumpInsn(199, localLabel3);
      _if_write_null(paramMethodVisitor, paramFieldInfo, paramContext);
      paramMethodVisitor.visitJumpInsn(167, localLabel4);
      paramMethodVisitor.visitLabel(localLabel3);
      paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
      paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "write", "(C)V");
      paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
      paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldName", "(Ljava/lang/String;)V");
      paramMethodVisitor.visitVarInsn(25, paramContext.var("list"));
      paramMethodVisitor.visitMethodInsn(185, ASMUtils.getType(List.class), "size", "()I");
      paramMethodVisitor.visitVarInsn(54, paramContext.var("int"));
      Label localLabel5 = new Label();
      Label localLabel6 = new Label();
      Label localLabel7 = new Label();
      paramMethodVisitor.visitLabel(localLabel5);
      paramMethodVisitor.visitVarInsn(21, paramContext.var("int"));
      paramMethodVisitor.visitInsn(3);
      paramMethodVisitor.visitJumpInsn(160, localLabel6);
      paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
      paramMethodVisitor.visitLdcInsn("[]");
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "write", "(Ljava/lang/String;)V");
      paramMethodVisitor.visitJumpInsn(167, localLabel7);
      paramMethodVisitor.visitLabel(localLabel6);
      paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
      paramMethodVisitor.visitVarInsn(25, paramContext.var("list"));
      paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)V");
      paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
      paramMethodVisitor.visitVarInsn(16, 91);
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "write", "(C)V");
      paramMethodVisitor.visitInsn(1);
      paramMethodVisitor.visitTypeInsn(192, ASMUtils.getType(ObjectSerializer.class));
      paramMethodVisitor.visitVarInsn(58, paramContext.var("list_ser"));
      Label localLabel8 = new Label();
      Label localLabel9 = new Label();
      paramMethodVisitor.visitInsn(3);
      paramMethodVisitor.visitVarInsn(54, paramContext.var("i"));
      paramMethodVisitor.visitLabel(localLabel8);
      paramMethodVisitor.visitVarInsn(21, paramContext.var("i"));
      paramMethodVisitor.visitVarInsn(21, paramContext.var("int"));
      paramMethodVisitor.visitInsn(4);
      paramMethodVisitor.visitInsn(100);
      paramMethodVisitor.visitJumpInsn(162, localLabel9);
      if (localObject != String.class)
        break;
      paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
      paramMethodVisitor.visitVarInsn(25, paramContext.var("list"));
      paramMethodVisitor.visitVarInsn(21, paramContext.var("i"));
      paramMethodVisitor.visitMethodInsn(185, ASMUtils.getType(List.class), "get", "(I)Ljava/lang/Object;");
      paramMethodVisitor.visitTypeInsn(192, ASMUtils.getType(String.class));
      paramMethodVisitor.visitVarInsn(16, 44);
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeString", "(Ljava/lang/String;C)V");
      paramMethodVisitor.visitIincInsn(paramContext.var("i"), 1);
      paramMethodVisitor.visitJumpInsn(167, localLabel8);
      paramMethodVisitor.visitLabel(localLabel9);
      if (localObject != String.class)
        break label1315;
      paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
      paramMethodVisitor.visitVarInsn(25, paramContext.var("list"));
      paramMethodVisitor.visitVarInsn(21, paramContext.var("int"));
      paramMethodVisitor.visitInsn(4);
      paramMethodVisitor.visitInsn(100);
      paramMethodVisitor.visitMethodInsn(185, ASMUtils.getType(List.class), "get", "(I)Ljava/lang/Object;");
      paramMethodVisitor.visitTypeInsn(192, ASMUtils.getType(String.class));
      paramMethodVisitor.visitVarInsn(16, 93);
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeString", "(Ljava/lang/String;C)V");
      paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "popContext", "()V");
      paramMethodVisitor.visitLabel(localLabel7);
      _seperator(paramMethodVisitor, paramContext);
      paramMethodVisitor.visitLabel(localLabel4);
      paramMethodVisitor.visitLabel(localLabel1);
      return;
    }
    paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
    paramMethodVisitor.visitVarInsn(25, paramContext.var("list"));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("i"));
    paramMethodVisitor.visitMethodInsn(185, ASMUtils.getType(List.class), "get", "(I)Ljava/lang/Object;");
    paramMethodVisitor.visitVarInsn(21, paramContext.var("i"));
    paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(Integer.class), "valueOf", "(I)Ljava/lang/Integer;");
    if ((localClass != null) && (Modifier.isPublic(localClass.getModifiers())))
    {
      paramMethodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc((Class)localObject)));
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;)V");
    }
    while (true)
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
      paramMethodVisitor.visitVarInsn(16, 44);
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "write", "(C)V");
      break;
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
    }
    label1315: paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
    paramMethodVisitor.visitVarInsn(25, paramContext.var("list"));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("i"));
    paramMethodVisitor.visitMethodInsn(185, ASMUtils.getType(List.class), "get", "(I)Ljava/lang/Object;");
    paramMethodVisitor.visitVarInsn(21, paramContext.var("i"));
    paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(Integer.class), "valueOf", "(I)Ljava/lang/Integer;");
    if ((localClass != null) && (Modifier.isPublic(localClass.getModifiers())))
    {
      paramMethodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc((Class)localObject)));
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;)V");
    }
    while (true)
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
      paramMethodVisitor.visitVarInsn(16, 93);
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "write", "(C)V");
      break;
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
    }
  }

  private void _long(Class<?> paramClass, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    Label localLabel = new Label();
    _nameApply(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    _get(paramMethodVisitor, paramContext, paramFieldInfo);
    paramMethodVisitor.visitVarInsn(55, paramContext.var("long", 2));
    _filters(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    paramMethodVisitor.visitVarInsn(22, paramContext.var("long", 2));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldValue", "(CLjava/lang/String;J)V");
    _seperator(paramMethodVisitor, paramContext);
    paramMethodVisitor.visitLabel(localLabel);
  }

  private void _nameApply(MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext, Label paramLabel)
  {
    paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
    paramMethodVisitor.visitVarInsn(25, paramContext.obj());
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "applyName", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;)Z");
    paramMethodVisitor.visitJumpInsn(153, paramLabel);
  }

  private void _object(Class<?> paramClass, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    Label localLabel = new Label();
    _nameApply(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    _get(paramMethodVisitor, paramContext, paramFieldInfo);
    paramMethodVisitor.visitVarInsn(58, paramContext.var("object"));
    _filters(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    _writeObject(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    paramMethodVisitor.visitLabel(localLabel);
  }

  private void _processKey(MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    Class localClass = paramFieldInfo.getFieldClass();
    paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
    paramMethodVisitor.visitVarInsn(25, paramContext.obj());
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    if (localClass == Byte.TYPE)
    {
      paramMethodVisitor.visitVarInsn(21, paramContext.var("byte"));
      paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "processKey", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;B)Ljava/lang/String;");
    }
    while (true)
    {
      paramMethodVisitor.visitVarInsn(58, paramContext.fieldName());
      return;
      if (localClass == Short.TYPE)
      {
        paramMethodVisitor.visitVarInsn(21, paramContext.var("short"));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "processKey", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;S)Ljava/lang/String;");
      }
      else if (localClass == Integer.TYPE)
      {
        paramMethodVisitor.visitVarInsn(21, paramContext.var("int"));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "processKey", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;I)Ljava/lang/String;");
      }
      else if (localClass == Character.TYPE)
      {
        paramMethodVisitor.visitVarInsn(21, paramContext.var("char"));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "processKey", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;C)Ljava/lang/String;");
      }
      else if (localClass == Long.TYPE)
      {
        paramMethodVisitor.visitVarInsn(22, paramContext.var("long", 2));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "processKey", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;J)Ljava/lang/String;");
      }
      else if (localClass == Float.TYPE)
      {
        paramMethodVisitor.visitVarInsn(23, paramContext.var("float"));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "processKey", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;F)Ljava/lang/String;");
      }
      else if (localClass == Double.TYPE)
      {
        paramMethodVisitor.visitVarInsn(24, paramContext.var("double", 2));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "processKey", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;D)Ljava/lang/String;");
      }
      else if (localClass == Boolean.TYPE)
      {
        paramMethodVisitor.visitVarInsn(21, paramContext.var("boolean"));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "processKey", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;Z)Ljava/lang/String;");
      }
      else if (localClass == BigDecimal.class)
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("decimal"));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "processKey", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;");
      }
      else if (localClass == String.class)
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("string"));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "processKey", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;");
      }
      else if (localClass.isEnum())
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("enum"));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "processKey", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;");
      }
      else if (List.class.isAssignableFrom(localClass))
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("list"));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "processKey", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;");
      }
      else
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("object"));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "processKey", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;");
      }
    }
  }

  private void _processValue(MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    Class localClass = paramFieldInfo.getFieldClass();
    paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
    paramMethodVisitor.visitVarInsn(25, paramContext.obj());
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    if (localClass == Byte.TYPE)
    {
      paramMethodVisitor.visitVarInsn(21, paramContext.var("byte"));
      paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(Byte.class), "valueOf", "(B)Ljava/lang/Byte;");
    }
    while (true)
    {
      paramMethodVisitor.visitVarInsn(58, paramContext.original());
      paramMethodVisitor.visitVarInsn(25, paramContext.original());
      paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(FilterUtils.class), "processValue", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;");
      paramMethodVisitor.visitVarInsn(58, paramContext.processValue());
      return;
      if (localClass == Short.TYPE)
      {
        paramMethodVisitor.visitVarInsn(21, paramContext.var("short"));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(Short.class), "valueOf", "(S)Ljava/lang/Short;");
      }
      else if (localClass == Integer.TYPE)
      {
        paramMethodVisitor.visitVarInsn(21, paramContext.var("int"));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(Integer.class), "valueOf", "(I)Ljava/lang/Integer;");
      }
      else if (localClass == Character.TYPE)
      {
        paramMethodVisitor.visitVarInsn(21, paramContext.var("char"));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(Character.class), "valueOf", "(C)Ljava/lang/Character;");
      }
      else if (localClass == Long.TYPE)
      {
        paramMethodVisitor.visitVarInsn(22, paramContext.var("long", 2));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(Long.class), "valueOf", "(J)Ljava/lang/Long;");
      }
      else if (localClass == Float.TYPE)
      {
        paramMethodVisitor.visitVarInsn(23, paramContext.var("float"));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(Float.class), "valueOf", "(F)Ljava/lang/Float;");
      }
      else if (localClass == Double.TYPE)
      {
        paramMethodVisitor.visitVarInsn(24, paramContext.var("double", 2));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(Double.class), "valueOf", "(D)Ljava/lang/Double;");
      }
      else if (localClass == Boolean.TYPE)
      {
        paramMethodVisitor.visitVarInsn(21, paramContext.var("boolean"));
        paramMethodVisitor.visitMethodInsn(184, ASMUtils.getType(Boolean.class), "valueOf", "(Z)Ljava/lang/Boolean;");
      }
      else if (localClass == BigDecimal.class)
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("decimal"));
      }
      else if (localClass == String.class)
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("string"));
      }
      else if (localClass.isEnum())
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("enum"));
      }
      else if (List.class.isAssignableFrom(localClass))
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("list"));
      }
      else
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("object"));
      }
    }
  }

  private void _seperator(MethodVisitor paramMethodVisitor, Context paramContext)
  {
    paramMethodVisitor.visitVarInsn(16, 44);
    paramMethodVisitor.visitVarInsn(54, paramContext.var("seperator"));
  }

  private void _short(Class<?> paramClass, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    Label localLabel = new Label();
    _nameApply(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    _get(paramMethodVisitor, paramContext, paramFieldInfo);
    paramMethodVisitor.visitVarInsn(54, paramContext.var("short"));
    _filters(paramMethodVisitor, paramFieldInfo, paramContext, localLabel);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    paramMethodVisitor.visitVarInsn(21, paramContext.var("short"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldValue", "(CLjava/lang/String;I)V");
    _seperator(paramMethodVisitor, paramContext);
    paramMethodVisitor.visitLabel(localLabel);
  }

  private void _string(Class<?> paramClass, MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext)
  {
    Label localLabel1 = new Label();
    _nameApply(paramMethodVisitor, paramFieldInfo, paramContext, localLabel1);
    _get(paramMethodVisitor, paramContext, paramFieldInfo);
    paramMethodVisitor.visitVarInsn(58, paramContext.var("string"));
    _filters(paramMethodVisitor, paramFieldInfo, paramContext, localLabel1);
    Label localLabel2 = new Label();
    Label localLabel3 = new Label();
    paramMethodVisitor.visitVarInsn(25, paramContext.var("string"));
    paramMethodVisitor.visitJumpInsn(199, localLabel2);
    _if_write_null(paramMethodVisitor, paramFieldInfo, paramContext);
    paramMethodVisitor.visitJumpInsn(167, localLabel3);
    paramMethodVisitor.visitLabel(localLabel2);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    paramMethodVisitor.visitVarInsn(25, paramContext.var("string"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldValue", "(CLjava/lang/String;Ljava/lang/String;)V");
    _seperator(paramMethodVisitor, paramContext);
    paramMethodVisitor.visitLabel(localLabel3);
    paramMethodVisitor.visitLabel(localLabel1);
  }

  private void _writeObject(MethodVisitor paramMethodVisitor, FieldInfo paramFieldInfo, Context paramContext, Label paramLabel)
  {
    String str = paramFieldInfo.getFormat();
    Label localLabel = new Label();
    paramMethodVisitor.visitVarInsn(25, paramContext.processValue());
    paramMethodVisitor.visitJumpInsn(199, localLabel);
    _if_write_null(paramMethodVisitor, paramFieldInfo, paramContext);
    paramMethodVisitor.visitJumpInsn(167, paramLabel);
    paramMethodVisitor.visitLabel(localLabel);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "write", "(C)V");
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFieldName", "(Ljava/lang/String;)V");
    paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
    paramMethodVisitor.visitVarInsn(25, paramContext.processValue());
    if (str != null)
    {
      paramMethodVisitor.visitLdcInsn(str);
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "writeWithFormat", "(Ljava/lang/Object;Ljava/lang/String;)V");
    }
    while (true)
    {
      _seperator(paramMethodVisitor, paramContext);
      return;
      paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
      if (((paramFieldInfo.getFieldType() instanceof Class)) && (((Class)paramFieldInfo.getFieldType()).isPrimitive()))
      {
        paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
      }
      else
      {
        paramMethodVisitor.visitVarInsn(25, 0);
        paramMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), paramFieldInfo.getName() + "_asm_fieldType", "Ljava/lang/reflect/Type;");
        paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;)V");
      }
    }
  }

  private void generateWriteAsArray(Class<?> paramClass, MethodVisitor paramMethodVisitor, List<FieldInfo> paramList, Context paramContext)
    throws Exception
  {
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(16, 91);
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "write", "(C)V");
    int i = paramList.size();
    if (i == 0)
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
      paramMethodVisitor.visitVarInsn(16, 93);
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "write", "(C)V");
      return;
    }
    int j = 0;
    label107: int k;
    label127: FieldInfo localFieldInfo;
    Class localClass;
    if (j < i)
    {
      if (j != i - 1)
        break label255;
      k = 93;
      localFieldInfo = (FieldInfo)paramList.get(j);
      localClass = localFieldInfo.getFieldClass();
      paramMethodVisitor.visitLdcInsn(localFieldInfo.getName());
      paramMethodVisitor.visitVarInsn(58, paramContext.fieldName());
      if ((localClass != Byte.TYPE) && (localClass != Short.TYPE) && (localClass != Integer.TYPE))
        break label262;
      paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
      _get(paramMethodVisitor, paramContext, localFieldInfo);
      paramMethodVisitor.visitVarInsn(16, k);
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeIntAndChar", "(IC)V");
    }
    while (true)
    {
      j++;
      break label107;
      break;
      label255: k = 44;
      break label127;
      label262: if (localClass == Long.TYPE)
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
        _get(paramMethodVisitor, paramContext, localFieldInfo);
        paramMethodVisitor.visitVarInsn(16, k);
        paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeLongAndChar", "(JC)V");
      }
      else if (localClass == Float.TYPE)
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
        _get(paramMethodVisitor, paramContext, localFieldInfo);
        paramMethodVisitor.visitVarInsn(16, k);
        paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeFloatAndChar", "(FC)V");
      }
      else if (localClass == Double.TYPE)
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
        _get(paramMethodVisitor, paramContext, localFieldInfo);
        paramMethodVisitor.visitVarInsn(16, k);
        paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeDoubleAndChar", "(DC)V");
      }
      else if (localClass == Boolean.TYPE)
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
        _get(paramMethodVisitor, paramContext, localFieldInfo);
        paramMethodVisitor.visitVarInsn(16, k);
        paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeBooleanAndChar", "(ZC)V");
      }
      else if (localClass == Character.TYPE)
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
        _get(paramMethodVisitor, paramContext, localFieldInfo);
        paramMethodVisitor.visitVarInsn(16, k);
        paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeCharacterAndChar", "(CC)V");
      }
      else if (localClass == String.class)
      {
        paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
        _get(paramMethodVisitor, paramContext, localFieldInfo);
        paramMethodVisitor.visitVarInsn(16, k);
        paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeString", "(Ljava/lang/String;C)V");
      }
      else
      {
        if (!localClass.isEnum())
          break label716;
        paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
        _get(paramMethodVisitor, paramContext, localFieldInfo);
        paramMethodVisitor.visitVarInsn(16, k);
        paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "writeEnum", "(Ljava/lang/Enum;C)V");
      }
    }
    label716: String str = localFieldInfo.getFormat();
    paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
    _get(paramMethodVisitor, paramContext, localFieldInfo);
    if (str != null)
    {
      paramMethodVisitor.visitLdcInsn(str);
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "writeWithFormat", "(Ljava/lang/Object;Ljava/lang/String;)V");
    }
    while (true)
    {
      paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
      paramMethodVisitor.visitVarInsn(16, k);
      paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "write", "(C)V");
      break;
      paramMethodVisitor.visitVarInsn(25, paramContext.fieldName());
      if (((localFieldInfo.getFieldType() instanceof Class)) && (((Class)localFieldInfo.getFieldType()).isPrimitive()))
      {
        paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
      }
      else
      {
        paramMethodVisitor.visitVarInsn(25, 0);
        paramMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), localFieldInfo.getName() + "_asm_fieldType", "Ljava/lang/reflect/Type;");
        paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;)V");
      }
    }
  }

  private void generateWriteMethod(Class<?> paramClass, MethodVisitor paramMethodVisitor, List<FieldInfo> paramList, Context paramContext)
    throws Exception
  {
    Label localLabel1 = new Label();
    int i = paramList.size();
    Label localLabel2 = new Label();
    Label localLabel3 = new Label();
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitFieldInsn(178, ASMUtils.getType(SerializerFeature.class), "PrettyFormat", "L" + ASMUtils.getType(SerializerFeature.class) + ";");
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "isEnabled", "(L" + ASMUtils.getType(SerializerFeature.class) + ";" + ")Z");
    paramMethodVisitor.visitJumpInsn(153, localLabel2);
    paramMethodVisitor.visitVarInsn(25, 0);
    paramMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), "nature", ASMUtils.getDesc(JavaBeanSerializer.class));
    paramMethodVisitor.visitJumpInsn(199, localLabel3);
    initNature(paramClass, paramMethodVisitor, paramContext);
    paramMethodVisitor.visitLabel(localLabel3);
    paramMethodVisitor.visitVarInsn(25, 0);
    paramMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), "nature", ASMUtils.getDesc(JavaBeanSerializer.class));
    paramMethodVisitor.visitVarInsn(25, 1);
    paramMethodVisitor.visitVarInsn(25, 2);
    paramMethodVisitor.visitVarInsn(25, 3);
    paramMethodVisitor.visitVarInsn(25, 4);
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JavaBeanSerializer.class), "write", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;)V");
    paramMethodVisitor.visitInsn(177);
    paramMethodVisitor.visitLabel(localLabel2);
    Label localLabel4 = new Label();
    Label localLabel5 = new Label();
    paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
    paramMethodVisitor.visitVarInsn(25, paramContext.obj());
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "containsReference", "(Ljava/lang/Object;)Z");
    paramMethodVisitor.visitJumpInsn(153, localLabel4);
    paramMethodVisitor.visitVarInsn(25, 0);
    paramMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), "nature", ASMUtils.getDesc(JavaBeanSerializer.class));
    paramMethodVisitor.visitJumpInsn(199, localLabel5);
    initNature(paramClass, paramMethodVisitor, paramContext);
    paramMethodVisitor.visitLabel(localLabel5);
    paramMethodVisitor.visitVarInsn(25, 0);
    paramMethodVisitor.visitFieldInsn(180, paramContext.getClassName(), "nature", ASMUtils.getDesc(JavaBeanSerializer.class));
    paramMethodVisitor.visitVarInsn(25, 1);
    paramMethodVisitor.visitVarInsn(25, 2);
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JavaBeanSerializer.class), "writeReference", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;)V");
    paramMethodVisitor.visitInsn(177);
    paramMethodVisitor.visitLabel(localLabel4);
    Label localLabel6 = new Label();
    paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
    paramMethodVisitor.visitVarInsn(25, paramContext.obj());
    paramMethodVisitor.visitVarInsn(25, paramContext.paramFieldType());
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "isWriteAsArray", "(Ljava/lang/Object;Ljava/lang/reflect/Type;)Z");
    paramMethodVisitor.visitJumpInsn(153, localLabel6);
    paramMethodVisitor.visitVarInsn(25, 0);
    paramMethodVisitor.visitVarInsn(25, 1);
    paramMethodVisitor.visitVarInsn(25, 2);
    paramMethodVisitor.visitVarInsn(25, 3);
    paramMethodVisitor.visitVarInsn(25, 4);
    paramMethodVisitor.visitMethodInsn(182, paramContext.getClassName(), "writeAsArray", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;)V");
    paramMethodVisitor.visitInsn(177);
    paramMethodVisitor.visitLabel(localLabel6);
    paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "getContext", "()Lcom/alibaba/fastjson/serializer/SerialContext;");
    paramMethodVisitor.visitVarInsn(58, paramContext.var("parent"));
    paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
    paramMethodVisitor.visitVarInsn(25, paramContext.var("parent"));
    paramMethodVisitor.visitVarInsn(25, paramContext.obj());
    paramMethodVisitor.visitVarInsn(25, paramContext.paramFieldName());
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "setContext", "(Lcom/alibaba/fastjson/serializer/SerialContext;Ljava/lang/Object;Ljava/lang/Object;)V");
    Label localLabel7 = new Label();
    Label localLabel8 = new Label();
    Label localLabel9 = new Label();
    paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
    paramMethodVisitor.visitVarInsn(25, paramContext.paramFieldType());
    paramMethodVisitor.visitVarInsn(25, paramContext.obj());
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "isWriteClassName", "(Ljava/lang/reflect/Type;Ljava/lang/Object;)Z");
    paramMethodVisitor.visitJumpInsn(153, localLabel8);
    paramMethodVisitor.visitVarInsn(25, paramContext.paramFieldType());
    paramMethodVisitor.visitVarInsn(25, paramContext.obj());
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(Object.class), "getClass", "()Ljava/lang/Class;");
    paramMethodVisitor.visitJumpInsn(165, localLabel8);
    paramMethodVisitor.visitLabel(localLabel9);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitLdcInsn("{\"" + JSON.DEFAULT_TYPE_KEY + "\":\"" + paramClass.getName() + "\"");
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "write", "(Ljava/lang/String;)V");
    paramMethodVisitor.visitVarInsn(16, 44);
    paramMethodVisitor.visitJumpInsn(167, localLabel7);
    paramMethodVisitor.visitLabel(localLabel8);
    paramMethodVisitor.visitVarInsn(16, 123);
    paramMethodVisitor.visitLabel(localLabel7);
    paramMethodVisitor.visitVarInsn(54, paramContext.var("seperator"));
    _before(paramMethodVisitor, paramContext);
    int j = 0;
    if (j < i)
    {
      FieldInfo localFieldInfo = (FieldInfo)paramList.get(j);
      Class localClass = localFieldInfo.getFieldClass();
      paramMethodVisitor.visitLdcInsn(localFieldInfo.getName());
      paramMethodVisitor.visitVarInsn(58, paramContext.fieldName());
      if (localClass == Byte.TYPE)
        _byte(paramClass, paramMethodVisitor, localFieldInfo, paramContext);
      while (true)
      {
        j++;
        break;
        if (localClass == Short.TYPE)
          _short(paramClass, paramMethodVisitor, localFieldInfo, paramContext);
        else if (localClass == Integer.TYPE)
          _int(paramClass, paramMethodVisitor, localFieldInfo, paramContext);
        else if (localClass == Long.TYPE)
          _long(paramClass, paramMethodVisitor, localFieldInfo, paramContext);
        else if (localClass == Float.TYPE)
          _float(paramClass, paramMethodVisitor, localFieldInfo, paramContext);
        else if (localClass == Double.TYPE)
          _double(paramClass, paramMethodVisitor, localFieldInfo, paramContext);
        else if (localClass == Boolean.TYPE)
          _boolean(paramClass, paramMethodVisitor, localFieldInfo, paramContext);
        else if (localClass == Character.TYPE)
          _char(paramClass, paramMethodVisitor, localFieldInfo, paramContext);
        else if (localClass == String.class)
          _string(paramClass, paramMethodVisitor, localFieldInfo, paramContext);
        else if (localClass == BigDecimal.class)
          _decimal(paramClass, paramMethodVisitor, localFieldInfo, paramContext);
        else if (List.class.isAssignableFrom(localClass))
          _list(paramClass, paramMethodVisitor, localFieldInfo, paramContext);
        else if (localClass.isEnum())
          _enum(paramClass, paramMethodVisitor, localFieldInfo, paramContext);
        else
          _object(paramClass, paramMethodVisitor, localFieldInfo, paramContext);
      }
    }
    _after(paramMethodVisitor, paramContext);
    Label localLabel10 = new Label();
    Label localLabel11 = new Label();
    paramMethodVisitor.visitVarInsn(21, paramContext.var("seperator"));
    paramMethodVisitor.visitIntInsn(16, 123);
    paramMethodVisitor.visitJumpInsn(160, localLabel10);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(16, 123);
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "write", "(C)V");
    paramMethodVisitor.visitLabel(localLabel10);
    paramMethodVisitor.visitVarInsn(25, paramContext.var("out"));
    paramMethodVisitor.visitVarInsn(16, 125);
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "write", "(C)V");
    paramMethodVisitor.visitLabel(localLabel11);
    paramMethodVisitor.visitLabel(localLabel1);
    paramMethodVisitor.visitVarInsn(25, paramContext.serializer());
    paramMethodVisitor.visitVarInsn(25, paramContext.var("parent"));
    paramMethodVisitor.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "setContext", "(Lcom/alibaba/fastjson/serializer/SerialContext;)V");
  }

  private void initNature(Class<?> paramClass, MethodVisitor paramMethodVisitor, Context paramContext)
  {
    paramMethodVisitor.visitVarInsn(25, 0);
    paramMethodVisitor.visitTypeInsn(187, ASMUtils.getType(JavaBeanSerializer.class));
    paramMethodVisitor.visitInsn(89);
    paramMethodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(paramClass)));
    paramMethodVisitor.visitMethodInsn(183, ASMUtils.getType(JavaBeanSerializer.class), "<init>", "(" + ASMUtils.getDesc(Class.class) + ")V");
    paramMethodVisitor.visitFieldInsn(181, paramContext.getClassName(), "nature", ASMUtils.getDesc(JavaBeanSerializer.class));
  }

  public ObjectSerializer createJavaBeanSerializer(Class<?> paramClass)
    throws Exception
  {
    return createJavaBeanSerializer(paramClass, (Map)null);
  }

  public ObjectSerializer createJavaBeanSerializer(Class<?> paramClass, Map<String, String> paramMap)
    throws Exception
  {
    if (paramClass.isPrimitive())
      throw new JSONException("unsupportd class " + paramClass.getName());
    List localList1 = TypeUtils.computeGetters(paramClass, paramMap, false);
    String str = getGenClassName(paramClass);
    ClassWriter localClassWriter = new ClassWriter();
    localClassWriter.visit(49, 33, str, "java/lang/Object", new String[] { "com/alibaba/fastjson/serializer/ObjectSerializer" });
    localClassWriter.visitField(2, "nature", ASMUtils.getDesc(JavaBeanSerializer.class)).visitEnd();
    Iterator localIterator1 = localList1.iterator();
    while (localIterator1.hasNext())
    {
      FieldInfo localFieldInfo2 = (FieldInfo)localIterator1.next();
      localClassWriter.visitField(1, localFieldInfo2.getName() + "_asm_fieldPrefix", "Ljava/lang/reflect/Type;").visitEnd();
      localClassWriter.visitField(1, localFieldInfo2.getName() + "_asm_fieldType", "Ljava/lang/reflect/Type;").visitEnd();
    }
    MethodVisitor localMethodVisitor1 = localClassWriter.visitMethod(1, "<init>", "()V", null, null);
    localMethodVisitor1.visitVarInsn(25, 0);
    localMethodVisitor1.visitMethodInsn(183, "java/lang/Object", "<init>", "()V");
    Iterator localIterator2 = localList1.iterator();
    if (localIterator2.hasNext())
    {
      FieldInfo localFieldInfo1 = (FieldInfo)localIterator2.next();
      localMethodVisitor1.visitVarInsn(25, 0);
      localMethodVisitor1.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(localFieldInfo1.getDeclaringClass())));
      if (localFieldInfo1.getMethod() != null)
      {
        localMethodVisitor1.visitLdcInsn(localFieldInfo1.getMethod().getName());
        localMethodVisitor1.visitMethodInsn(184, ASMUtils.getType(ASMUtils.class), "getMethodType", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Type;");
      }
      while (true)
      {
        localMethodVisitor1.visitFieldInsn(181, str, localFieldInfo1.getName() + "_asm_fieldType", "Ljava/lang/reflect/Type;");
        break;
        localMethodVisitor1.visitLdcInsn(localFieldInfo1.getField().getName());
        localMethodVisitor1.visitMethodInsn(184, ASMUtils.getType(ASMUtils.class), "getFieldType", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Type;");
      }
    }
    localMethodVisitor1.visitInsn(177);
    localMethodVisitor1.visitMaxs(4, 4);
    localMethodVisitor1.visitEnd();
    Context localContext1 = new Context(str);
    MethodVisitor localMethodVisitor2 = localClassWriter.visitMethod(1, "write", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;)V", null, new String[] { "java/io/IOException" });
    localMethodVisitor2.visitVarInsn(25, localContext1.serializer());
    localMethodVisitor2.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "getWriter", "()" + ASMUtils.getDesc(SerializeWriter.class));
    localMethodVisitor2.visitVarInsn(58, localContext1.var("out"));
    JSONType localJSONType = (JSONType)paramClass.getAnnotation(JSONType.class);
    if ((localJSONType == null) || (localJSONType.alphabetic()))
    {
      Label localLabel = new Label();
      localMethodVisitor2.visitVarInsn(25, localContext1.var("out"));
      localMethodVisitor2.visitFieldInsn(178, ASMUtils.getType(SerializerFeature.class), "SortField", "L" + ASMUtils.getType(SerializerFeature.class) + ";");
      localMethodVisitor2.visitMethodInsn(182, ASMUtils.getType(SerializeWriter.class), "isEnabled", "(L" + ASMUtils.getType(SerializerFeature.class) + ";" + ")Z");
      localMethodVisitor2.visitJumpInsn(153, localLabel);
      localMethodVisitor2.visitVarInsn(25, 0);
      localMethodVisitor2.visitVarInsn(25, 1);
      localMethodVisitor2.visitVarInsn(25, 2);
      localMethodVisitor2.visitVarInsn(25, 3);
      localMethodVisitor2.visitVarInsn(25, localContext1.paramFieldType());
      localMethodVisitor2.visitMethodInsn(182, str, "write1", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;)V");
      localMethodVisitor2.visitInsn(177);
      localMethodVisitor2.visitLabel(localLabel);
    }
    localMethodVisitor2.visitVarInsn(25, localContext1.obj());
    localMethodVisitor2.visitTypeInsn(192, ASMUtils.getType(paramClass));
    localMethodVisitor2.visitVarInsn(58, localContext1.var("entity"));
    generateWriteMethod(paramClass, localMethodVisitor2, localList1, localContext1);
    localMethodVisitor2.visitInsn(177);
    localMethodVisitor2.visitMaxs(5, 1 + localContext1.getVariantCount());
    localMethodVisitor2.visitEnd();
    List localList2 = TypeUtils.computeGetters(paramClass, paramMap, true);
    Context localContext2 = new Context(str);
    MethodVisitor localMethodVisitor3 = localClassWriter.visitMethod(1, "write1", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;)V", null, new String[] { "java/io/IOException" });
    localMethodVisitor3.visitVarInsn(25, localContext2.serializer());
    localMethodVisitor3.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "getWriter", "()" + ASMUtils.getDesc(SerializeWriter.class));
    localMethodVisitor3.visitVarInsn(58, localContext2.var("out"));
    localMethodVisitor3.visitVarInsn(25, localContext2.obj());
    localMethodVisitor3.visitTypeInsn(192, ASMUtils.getType(paramClass));
    localMethodVisitor3.visitVarInsn(58, localContext2.var("entity"));
    generateWriteMethod(paramClass, localMethodVisitor3, localList2, localContext2);
    localMethodVisitor3.visitInsn(177);
    localMethodVisitor3.visitMaxs(5, 1 + localContext2.getVariantCount());
    localMethodVisitor3.visitEnd();
    Context localContext3 = new Context(str);
    MethodVisitor localMethodVisitor4 = localClassWriter.visitMethod(1, "writeAsArray", "(Lcom/alibaba/fastjson/serializer/JSONSerializer;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;)V", null, new String[] { "java/io/IOException" });
    localMethodVisitor4.visitVarInsn(25, localContext3.serializer());
    localMethodVisitor4.visitMethodInsn(182, ASMUtils.getType(JSONSerializer.class), "getWriter", "()" + ASMUtils.getDesc(SerializeWriter.class));
    localMethodVisitor4.visitVarInsn(58, localContext3.var("out"));
    localMethodVisitor4.visitVarInsn(25, localContext3.obj());
    localMethodVisitor4.visitTypeInsn(192, ASMUtils.getType(paramClass));
    localMethodVisitor4.visitVarInsn(58, localContext3.var("entity"));
    generateWriteAsArray(paramClass, localMethodVisitor4, localList2, localContext3);
    localMethodVisitor4.visitInsn(177);
    localMethodVisitor4.visitMaxs(5, 1 + localContext3.getVariantCount());
    localMethodVisitor4.visitEnd();
    byte[] arrayOfByte = localClassWriter.toByteArray();
    return (ObjectSerializer)this.classLoader.defineClassPublic(str, arrayOfByte, 0, arrayOfByte.length).newInstance();
  }

  public String getGenClassName(Class<?> paramClass)
  {
    return "Serializer_" + this.seed.incrementAndGet();
  }

  public boolean isExternalClass(Class<?> paramClass)
  {
    return this.classLoader.isExternalClass(paramClass);
  }

  static class Context
  {
    private final String className;
    private int variantIndex = 8;
    private Map<String, Integer> variants = new HashMap();

    public Context(String paramString)
    {
      this.className = paramString;
    }

    public int fieldName()
    {
      return 5;
    }

    public String getClassName()
    {
      return this.className;
    }

    public int getVariantCount()
    {
      return this.variantIndex;
    }

    public int obj()
    {
      return 2;
    }

    public int original()
    {
      return 6;
    }

    public int paramFieldName()
    {
      return 3;
    }

    public int paramFieldType()
    {
      return 4;
    }

    public int processValue()
    {
      return 7;
    }

    public int serializer()
    {
      return 1;
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
 * Qualified Name:     com.alibaba.fastjson.serializer.ASMSerializerFactory
 * JD-Core Version:    0.6.2
 */