/*
 * Copyright 2018-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.litho.processor.integration.resources;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.HotswapManager;
import java.lang.reflect.Method;

/** @see com.facebook.litho.processor.integration.resources.SimpleLayoutSpec */
public final class SimpleLayout extends Component {
  private SimpleLayout() {
    super("SimpleLayout");
  }

  @Override
  protected Component onCreateLayout(ComponentContext context) {
    ClassLoader classLoader = HotswapManager.getClassLoader();
    Class specClass;
    try {
      specClass =
          classLoader.loadClass(
              "com.facebook.litho.processor.integration.resources.SimpleLayoutSpec");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    Component _result;
    try {
      final Method method = specClass.getDeclaredMethod("onCreateLayout", ComponentContext.class);
      method.setAccessible(true);
      _result = (Component) method.invoke(null, (ComponentContext) context);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return _result;
  }

  public static Builder create(ComponentContext context) {
    return create(context, 0, 0);
  }

  public static Builder create(ComponentContext context, int defStyleAttr, int defStyleRes) {
    final Builder builder = new Builder();
    SimpleLayout instance = new SimpleLayout();
    builder.init(context, defStyleAttr, defStyleRes, instance);
    return builder;
  }

  public static class Builder extends Component.Builder<Builder> {
    SimpleLayout mSimpleLayout;

    ComponentContext mContext;

    private void init(ComponentContext context, int defStyleAttr, int defStyleRes,
        SimpleLayout simpleLayoutRef) {
      super.init(context, defStyleAttr, defStyleRes, simpleLayoutRef);
      mSimpleLayout = simpleLayoutRef;
      mContext = context;
    }

    @Override
    public Builder getThis() {
      return this;
    }

    @Override
    public SimpleLayout build() {
      SimpleLayout simpleLayoutRef = mSimpleLayout;
      release();
      return simpleLayoutRef;
    }

    @Override
    protected void release() {
      super.release();
      mSimpleLayout = null;
      mContext = null;
    }
  }
}
