// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.apache.doris.catalog;

import org.apache.doris.common.AnalysisException;
import org.apache.doris.common.DdlException;
import org.apache.doris.common.MetaNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Database interface.
 * TODO:
 * I just copied some common interface from the origin Database class.
 * Maybe changed later.
 */
public interface DatabaseIf {

    void readLock();

    void readUnlock();

    void writeLock();

    void writeUnlock();

    boolean tryWriteLock(long timeout, TimeUnit unit);

    boolean isWriteLockHeldByCurrentThread();

    boolean writeLockIfExist();

    <E extends Exception>
    void writeLockOrException(E e) throws E;

    void writeLockOrDdlException() throws DdlException;

    long getId();

    String getFullName();

    DatabaseProperty getDbProperties();

    boolean isTableExist(String tableName);

    List<Table> getTables();

    List<Table> getTablesOnIdOrder();

    List<Table> getViews();

    List<Table> getTablesOnIdOrderIfExist(List<Long> tableIdList);

    List<Table> getTablesOnIdOrderOrThrowException(List<Long> tableIdList) throws MetaNotFoundException;

    Set<String> getTableNamesWithLock();

    Table getTableNullable(String tableName);

    Optional<Table> getTable(String tableName);

    Optional<Table> getTable(long tableId);

    <E extends Exception>
    Table getTableOrException(String tableName, java.util.function.Function<String, E> e) throws E;

    <E extends Exception>
    Table getTableOrException(long tableId, java.util.function.Function<Long, E> e) throws E;

    Table getTableOrMetaException(String tableName) throws MetaNotFoundException;

    Table getTableOrMetaException(long tableId) throws MetaNotFoundException;

    @SuppressWarnings("unchecked")
    <T extends Table>
    T getTableOrMetaException(String tableName, Table.TableType tableType) throws MetaNotFoundException;

    @SuppressWarnings("unchecked")
    <T extends Table>
    T getTableOrMetaException(long tableId, Table.TableType tableType) throws MetaNotFoundException;

    Table getTableOrDdlException(String tableName) throws DdlException;

    Table getTableOrDdlException(long tableId) throws DdlException;

    Table getTableOrAnalysisException(String tableName) throws AnalysisException;

    OlapTable getOlapTableOrAnalysisException(String tableName) throws AnalysisException;

    Table getTableOrAnalysisException(long tableId) throws AnalysisException;
}
