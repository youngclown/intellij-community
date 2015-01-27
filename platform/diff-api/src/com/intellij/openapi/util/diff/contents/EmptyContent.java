/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.openapi.util.diff.contents;

import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.annotations.Nullable;

/**
 * Represents empty content
 * <p/>
 * ex: 'Before' state for new file
 */
public class EmptyContent implements DiffContent {
  @Nullable
  @Override
  public FileType getContentType() {
    return null;
  }

  @Nullable
  @Override
  public OpenFileDescriptor getOpenFileDescriptor() {
    return null;
  }

  @Override
  public void onAssigned(boolean isAssigned) {
  }
}
