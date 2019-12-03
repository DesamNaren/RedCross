package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new in.gov.cgg.redcrossphase1.DataBinderMapperImpl());
  }
}
