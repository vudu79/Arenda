// Generated by Dagger (https://dagger.dev).
package com.example.navigationexample.domain.usecase.validation.validators;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DocumentNumber_Factory implements Factory<DocumentNumber> {
  @Override
  public DocumentNumber get() {
    return newInstance();
  }

  public static DocumentNumber_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DocumentNumber newInstance() {
    return new DocumentNumber();
  }

  private static final class InstanceHolder {
    private static final DocumentNumber_Factory INSTANCE = new DocumentNumber_Factory();
  }
}
