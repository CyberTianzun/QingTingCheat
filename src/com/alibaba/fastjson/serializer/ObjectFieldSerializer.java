package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.util.FieldInfo;
import java.util.Collection;

public class ObjectFieldSerializer extends FieldSerializer
{
  private ObjectSerializer fieldSerializer;
  private String format;
  private Class<?> runtimeFieldClass;
  boolean writeEnumUsingToString = false;
  boolean writeNullBooleanAsFalse = false;
  boolean writeNullListAsEmpty = false;
  boolean writeNullStringAsEmpty = false;
  private boolean writeNumberAsZero = false;

  public ObjectFieldSerializer(FieldInfo paramFieldInfo)
  {
    super(paramFieldInfo);
    JSONField localJSONField = (JSONField)paramFieldInfo.getAnnotation(JSONField.class);
    if (localJSONField != null)
    {
      this.format = localJSONField.format();
      if (this.format.trim().length() == 0)
        this.format = null;
      SerializerFeature[] arrayOfSerializerFeature = localJSONField.serialzeFeatures();
      int i = arrayOfSerializerFeature.length;
      int j = 0;
      if (j < i)
      {
        SerializerFeature localSerializerFeature = arrayOfSerializerFeature[j];
        if (localSerializerFeature == SerializerFeature.WriteNullNumberAsZero)
          this.writeNumberAsZero = true;
        while (true)
        {
          j++;
          break;
          if (localSerializerFeature == SerializerFeature.WriteNullStringAsEmpty)
            this.writeNullStringAsEmpty = true;
          else if (localSerializerFeature == SerializerFeature.WriteNullBooleanAsFalse)
            this.writeNullBooleanAsFalse = true;
          else if (localSerializerFeature == SerializerFeature.WriteNullListAsEmpty)
            this.writeNullListAsEmpty = true;
          else if (localSerializerFeature == SerializerFeature.WriteEnumUsingToString)
            this.writeEnumUsingToString = true;
        }
      }
    }
  }

  public void writeProperty(JSONSerializer paramJSONSerializer, Object paramObject)
    throws Exception
  {
    writePrefix(paramJSONSerializer);
    writeValue(paramJSONSerializer, paramObject);
  }

  public void writeValue(JSONSerializer paramJSONSerializer, Object paramObject)
    throws Exception
  {
    if (this.format != null)
    {
      paramJSONSerializer.writeWithFormat(paramObject, this.format);
      return;
    }
    if (this.fieldSerializer == null)
      if (paramObject != null)
        break label84;
    label84: for (this.runtimeFieldClass = this.fieldInfo.getFieldClass(); ; this.runtimeFieldClass = paramObject.getClass())
    {
      this.fieldSerializer = paramJSONSerializer.getObjectWriter(this.runtimeFieldClass);
      if (paramObject != null)
        break label196;
      if ((!this.writeNumberAsZero) || (!Number.class.isAssignableFrom(this.runtimeFieldClass)))
        break;
      paramJSONSerializer.getWriter().write('0');
      return;
    }
    if ((this.writeNullStringAsEmpty) && (String.class == this.runtimeFieldClass))
    {
      paramJSONSerializer.getWriter().write("\"\"");
      return;
    }
    if ((this.writeNullBooleanAsFalse) && (Boolean.class == this.runtimeFieldClass))
    {
      paramJSONSerializer.getWriter().write("false");
      return;
    }
    if ((this.writeNullListAsEmpty) && (Collection.class.isAssignableFrom(this.runtimeFieldClass)))
    {
      paramJSONSerializer.getWriter().write("[]");
      return;
    }
    this.fieldSerializer.write(paramJSONSerializer, null, this.fieldInfo.getName(), null);
    return;
    label196: if ((this.writeEnumUsingToString == true) && (this.runtimeFieldClass.isEnum()))
    {
      paramJSONSerializer.getWriter().writeString(((Enum)paramObject).name());
      return;
    }
    Class localClass = paramObject.getClass();
    if (localClass == this.runtimeFieldClass)
    {
      this.fieldSerializer.write(paramJSONSerializer, paramObject, this.fieldInfo.getName(), this.fieldInfo.getFieldType());
      return;
    }
    paramJSONSerializer.getObjectWriter(localClass).write(paramJSONSerializer, paramObject, this.fieldInfo.getName(), this.fieldInfo.getFieldType());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.ObjectFieldSerializer
 * JD-Core Version:    0.6.2
 */