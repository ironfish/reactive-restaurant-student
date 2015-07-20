/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.typesafe.training.restaurant

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.{ Inspectors, Matchers, WordSpec }

abstract class BaseSpec extends WordSpec with Matchers with TypeCheckedTripleEquals with Inspectors
