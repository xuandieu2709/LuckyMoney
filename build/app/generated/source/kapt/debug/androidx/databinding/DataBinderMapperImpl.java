package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new vn.xdeuhug.luckyMoney.DataBinderMapperImpl());
  }
}
