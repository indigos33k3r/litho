/*
 * Copyright 2014-present Facebook, Inc.
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

package com.facebook.litho;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import android.support.annotation.Nullable;
import com.facebook.litho.testing.testrunner.ComponentsTestRunner;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

@RunWith(ComponentsTestRunner.class)
public class LogTreePopulatorTest {
  private ComponentContext mContext;

  @Before
  public void setup() throws Exception {
    mContext = new ComponentContext(RuntimeEnvironment.application);
  }

  @Test
  public void testCustomTreePropLogger() {
    final BaseComponentsLogger logger =
        new TestComponentsLogger() {
          @Nullable
          @Override
          public Map<String, String> getExtraAnnotations(TreeProps treeProps) {
            final Object o = treeProps.get(MyKey.class);

            final Map<String, String> map = new HashMap<>(1);
            map.put("my_key", String.valueOf((int) o));

            return map;
          }
        };

    final PerfEvent event = mock(PerfEvent.class);
    final TreeProps treeProps = new TreeProps();
    treeProps.put(MyKey.class, 1337);
    mContext.setTreeProps(treeProps);

    LogTreePopulator.populatePerfEventFromLogger(mContext, logger, event);

    verify(event).markerAnnotate("my_key", "1337");
  }

  @Test
  public void testNullTreePropLogger() {
    final BaseComponentsLogger logger =
        new TestComponentsLogger() {
          @Nullable
          @Override
          public Map<String, String> getExtraAnnotations(TreeProps treeProps) {
            return null;
          }
        };

    final PerfEvent event = mock(PerfEvent.class);
    final TreeProps treeProps = new TreeProps();
    treeProps.put(MyKey.class, 1337);
    mContext.setTreeProps(treeProps);

    LogTreePopulator.populatePerfEventFromLogger(mContext, logger, event);

    verify(event, never()).markerAnnotate(anyString(), anyString());
  }

  private static class MyKey {}
}