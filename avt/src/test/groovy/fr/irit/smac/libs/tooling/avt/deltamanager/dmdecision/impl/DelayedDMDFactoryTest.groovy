/*
 * #%L
 * avt
 * %%
 * Copyright (C) 2014 - 2015 IRIT - SMAC Team
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package fr.irit.smac.libs.tooling.avt.deltamanager.dmdecision.impl

import spock.lang.Specification
import spock.lang.Unroll
import fr.irit.smac.libs.tooling.avt.deltamanager.dmdecision.IDMDecision

@Unroll
class DelayedDMDFactoryTest extends Specification{

    def 'DelayedDMDFactory' () {

        DelayedDMDFactory delayedDMDFactory

        when:
        delayedDMDFactory = new DelayedDMDFactory(new StandardDMDFactory(), 1, 1)

        then:
        delayedDMDFactory != null
    }

    def 'createInstance' () {

        given:
        DelayedDMDFactory delayedDMDFactory = new DelayedDMDFactory(new StandardDMDFactory(), 1, 1)

        when:
        IDMDecision delayedDMD = delayedDMDFactory.createInstance()

        then:
        delayedDMD != null
    }
}