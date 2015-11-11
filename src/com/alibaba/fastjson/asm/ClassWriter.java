package com.alibaba.fastjson.asm;

public class ClassWriter
{
  static final int ACC_SYNTHETIC_ATTRIBUTE = 262144;
  static final int CLASS = 7;
  public static final int COMPUTE_FRAMES = 2;
  public static final int COMPUTE_MAXS = 1;
  static final int DOUBLE = 6;
  static final int FIELD = 9;
  static final int FIELDORMETH_INSN = 6;
  static final int FLOAT = 4;
  static final int IINC_INSN = 12;
  static final int IMETH = 11;
  static final int IMPLVAR_INSN = 4;
  static final int INT = 3;
  static final int ITFDYNMETH_INSN = 7;
  static final int LABELW_INSN = 9;
  static final int LABEL_INSN = 8;
  static final int LDCW_INSN = 11;
  static final int LDC_INSN = 10;
  static final int LONG = 5;
  static final int LOOK_INSN = 14;
  static final int MANA_INSN = 15;
  static final int METH = 10;
  static final int NAME_TYPE = 12;
  static final int NOARG_INSN = 0;
  static final int SBYTE_INSN = 1;
  static final int SHORT_INSN = 2;
  static final int STR = 8;
  static final int TABL_INSN = 13;
  static final byte[] TYPE = arrayOfByte;
  static final int TYPE_INSN = 5;
  static final int TYPE_MERGED = 15;
  static final int TYPE_NORMAL = 13;
  static final int TYPE_UNINIT = 14;
  static final int UTF8 = 1;
  static final int VAR_INSN = 3;
  static final int WIDE_INSN = 16;
  private int access;
  FieldWriter firstField;
  MethodWriter firstMethod;
  int index = 1;
  private int interfaceCount;
  private int[] interfaces;
  Item[] items = new Item[256];
  final Item key = new Item();
  final Item key2 = new Item();
  final Item key3 = new Item();
  FieldWriter lastField;
  MethodWriter lastMethod;
  private int name;
  final ByteVector pool = new ByteVector();
  private int superName;
  String thisName;
  int threshold = (int)(0.75D * this.items.length);
  Item[] typeTable;
  int version;

  static
  {
    byte[] arrayOfByte = new byte['Ü'];
    for (int i = 0; i < arrayOfByte.length; i++)
      arrayOfByte[i] = ((byte)('\0'7' + "AAAAAAAAAAAAAAAABCKLLDDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAADDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMAAAAAAAAAAAAAAAAAAAAIIIIIIIIIIIIIIIIDNOAAAAAAGGGGGGGHHFBFAAFFAAQPIIJJIIIIIIIIIIIIIIIIII".charAt(i)));
  }

  public ClassWriter()
  {
    this(0);
  }

  private ClassWriter(int paramInt)
  {
  }

  private Item get(Item paramItem)
  {
    for (Item localItem = this.items[(paramItem.hashCode % this.items.length)]; (localItem != null) && ((localItem.type != paramItem.type) || (!paramItem.isEqualTo(localItem))); localItem = localItem.next);
    return localItem;
  }

  private Item newString(String paramString)
  {
    this.key2.set(8, paramString, null, null);
    Item localItem = get(this.key2);
    if (localItem == null)
    {
      this.pool.put12(8, newUTF8(paramString));
      int i = this.index;
      this.index = (i + 1);
      localItem = new Item(i, this.key2);
      put(localItem);
    }
    return localItem;
  }

  private void put(Item paramItem)
  {
    if (this.index > this.threshold)
    {
      int j = this.items.length;
      int k = 1 + j * 2;
      Item[] arrayOfItem = new Item[k];
      for (int m = j - 1; m >= 0; m--)
      {
        Item localItem;
        for (Object localObject = this.items[m]; localObject != null; localObject = localItem)
        {
          int n = ((Item)localObject).hashCode % arrayOfItem.length;
          localItem = ((Item)localObject).next;
          ((Item)localObject).next = arrayOfItem[n];
          arrayOfItem[n] = localObject;
        }
      }
      this.items = arrayOfItem;
      this.threshold = ((int)(0.75D * k));
    }
    int i = paramItem.hashCode % this.items.length;
    paramItem.next = this.items[i];
    this.items[i] = paramItem;
  }

  private void put122(int paramInt1, int paramInt2, int paramInt3)
  {
    this.pool.put12(paramInt1, paramInt2).putShort(paramInt3);
  }

  public int newClass(String paramString)
  {
    return newClassItem(paramString).index;
  }

  Item newClassItem(String paramString)
  {
    this.key2.set(7, paramString, null, null);
    Item localItem = get(this.key2);
    if (localItem == null)
    {
      this.pool.put12(7, newUTF8(paramString));
      int i = this.index;
      this.index = (i + 1);
      localItem = new Item(i, this.key2);
      put(localItem);
    }
    return localItem;
  }

  Item newConstItem(Object paramObject)
  {
    if ((paramObject instanceof Integer))
      return newInteger(((Integer)paramObject).intValue());
    if ((paramObject instanceof String))
      return newString((String)paramObject);
    if ((paramObject instanceof Type))
    {
      Type localType = (Type)paramObject;
      if (localType.getSort() == 10);
      for (String str = localType.getInternalName(); ; str = localType.getDescriptor())
        return newClassItem(str);
    }
    throw new IllegalArgumentException("value " + paramObject);
  }

  Item newFieldItem(String paramString1, String paramString2, String paramString3)
  {
    this.key3.set(9, paramString1, paramString2, paramString3);
    Item localItem = get(this.key3);
    if (localItem == null)
    {
      put122(9, newClass(paramString1), newNameType(paramString2, paramString3));
      int i = this.index;
      this.index = (i + 1);
      localItem = new Item(i, this.key3);
      put(localItem);
    }
    return localItem;
  }

  Item newInteger(int paramInt)
  {
    this.key.set(paramInt);
    Item localItem = get(this.key);
    if (localItem == null)
    {
      this.pool.putByte(3).putInt(paramInt);
      int i = this.index;
      this.index = (i + 1);
      localItem = new Item(i, this.key);
      put(localItem);
    }
    return localItem;
  }

  Item newMethodItem(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    if (paramBoolean);
    for (int i = 11; ; i = 10)
    {
      this.key3.set(i, paramString1, paramString2, paramString3);
      Item localItem = get(this.key3);
      if (localItem == null)
      {
        put122(i, newClass(paramString1), newNameType(paramString2, paramString3));
        int j = this.index;
        this.index = (j + 1);
        localItem = new Item(j, this.key3);
        put(localItem);
      }
      return localItem;
    }
  }

  public int newNameType(String paramString1, String paramString2)
  {
    return newNameTypeItem(paramString1, paramString2).index;
  }

  Item newNameTypeItem(String paramString1, String paramString2)
  {
    this.key2.set(12, paramString1, paramString2, null);
    Item localItem = get(this.key2);
    if (localItem == null)
    {
      put122(12, newUTF8(paramString1), newUTF8(paramString2));
      int i = this.index;
      this.index = (i + 1);
      localItem = new Item(i, this.key2);
      put(localItem);
    }
    return localItem;
  }

  public int newUTF8(String paramString)
  {
    this.key.set(1, paramString, null, null);
    Item localItem = get(this.key);
    if (localItem == null)
    {
      this.pool.putByte(1).putUTF8(paramString);
      int i = this.index;
      this.index = (i + 1);
      localItem = new Item(i, this.key);
      put(localItem);
    }
    return localItem.index;
  }

  public byte[] toByteArray()
  {
    int i = 24 + 2 * this.interfaceCount;
    int j = 0;
    for (FieldWriter localFieldWriter1 = this.firstField; localFieldWriter1 != null; localFieldWriter1 = localFieldWriter1.next)
    {
      j++;
      i += localFieldWriter1.getSize();
    }
    int k = 0;
    for (MethodWriter localMethodWriter1 = this.firstMethod; localMethodWriter1 != null; localMethodWriter1 = localMethodWriter1.next)
    {
      k++;
      i += localMethodWriter1.getSize();
    }
    ByteVector localByteVector = new ByteVector(i + this.pool.length);
    localByteVector.putInt(-889275714).putInt(this.version);
    localByteVector.putShort(this.index).putByteArray(this.pool.data, 0, this.pool.length);
    int m = 0x60000 | (0x40000 & this.access) / 64;
    localByteVector.putShort(this.access & (m ^ 0xFFFFFFFF)).putShort(this.name).putShort(this.superName);
    localByteVector.putShort(this.interfaceCount);
    for (int n = 0; n < this.interfaceCount; n++)
      localByteVector.putShort(this.interfaces[n]);
    localByteVector.putShort(j);
    for (FieldWriter localFieldWriter2 = this.firstField; localFieldWriter2 != null; localFieldWriter2 = localFieldWriter2.next)
      localFieldWriter2.put(localByteVector);
    localByteVector.putShort(k);
    for (MethodWriter localMethodWriter2 = this.firstMethod; localMethodWriter2 != null; localMethodWriter2 = localMethodWriter2.next)
      localMethodWriter2.put(localByteVector);
    localByteVector.putShort(0);
    return localByteVector.data;
  }

  public void visit(int paramInt1, int paramInt2, String paramString1, String paramString2, String[] paramArrayOfString)
  {
    this.version = paramInt1;
    this.access = paramInt2;
    this.name = newClass(paramString1);
    this.thisName = paramString1;
    if (paramString2 == null);
    for (int i = 0; ; i = newClass(paramString2))
    {
      this.superName = i;
      if ((paramArrayOfString == null) || (paramArrayOfString.length <= 0))
        break;
      this.interfaceCount = paramArrayOfString.length;
      this.interfaces = new int[this.interfaceCount];
      for (int j = 0; j < this.interfaceCount; j++)
        this.interfaces[j] = newClass(paramArrayOfString[j]);
    }
  }

  public FieldVisitor visitField(int paramInt, String paramString1, String paramString2)
  {
    return new FieldWriter(this, paramInt, paramString1, paramString2);
  }

  public MethodVisitor visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString)
  {
    return new MethodWriter(this, paramInt, paramString1, paramString2, paramString3, paramArrayOfString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.asm.ClassWriter
 * JD-Core Version:    0.6.2
 */