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
public final class PledgeValidation_Factory implements Factory<PledgeValidation> {
  @Override
  public PledgeValidation get() {
    return newInstance();
  }

  public static PledgeValidation_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static PledgeValidation newInstance() {
    return new PledgeValidation();
  }

  private static final class InstanceHolder {
    private static final PledgeValidation_Factory INSTANCE = new PledgeValidation_Factory();
  }
}
