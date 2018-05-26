/*
 * Copyright 2018 Confluent Inc.
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

package io.confluent.ksql.function;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Map;

import io.confluent.ksql.util.KsqlException;

public class AggregateFunctionArguments {

  private final int udafIndex;
  private final List<String> args;

  public AggregateFunctionArguments(final Map<String, Integer> expressionNames,
                                    final List<String> args) {
    Preconditions.checkArgument(expressionNames != null && !expressionNames.isEmpty(),
        "expressionNames can't be null or empty");
    Preconditions.checkArgument(args != null && !args.isEmpty(), "args can't be null or empty");
    this.udafIndex = expressionNames.get(args.get(0));
    this.args = ImmutableList.copyOf(args);
  }

  public int udafIndex() {
    return udafIndex;
  }

  public String arg(final int i) {
    return args.get(i);
  }

  public void ensureArgCount(final int expectedCount, final String functionName) {
    if (args.size() != expectedCount) {
      throw new KsqlException(
          String.format("Invalid parameter count for %s. Need %d args, got %d arg(s)",
              functionName, expectedCount, args.size()));
    }
  }

}
