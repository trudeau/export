package org.nnsoft.trudeau.export;

/*
 *   Copyright 2013 The Trudeau Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

/**
 * TODO Fill me!
 *
 * @param <V> the Graph vertices type.
 * @param <E> the Graph edges type.
 */
public interface NamedExportSelector<V, E>
    extends ExportSelector<V, E>
{

    /**
     * Use the given name when exporting the {@link org.apache.commons.graph.Graph} to a resource.
     *
     * @param name the name to identify the {@link org.apache.commons.graph.Graph}
     * @return the graph export format selector
     */
    ExportSelector<V, E> withName( String name );

}
