/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.internal.compiler.parser;

/*An interface that contains static declarations for some basic information
 about the parser such as the number of rules in the grammar, the starting state, etc...*/

public interface ParserBasicInformation {

	public final static int
      ERROR_SYMBOL      = 108,
      MAX_NAME_LENGTH   = 36,
      NUM_STATES        = 914,
      NT_OFFSET         = 108,
      SCOPE_UBOUND      = 103,
      SCOPE_SIZE        = 104,
      LA_STATE_OFFSET   = 9951,
      MAX_LA            = 1,
      NUM_RULES         = 642,
      NUM_TERMINALS     = 108,
      NUM_NON_TERMINALS = 281,
      NUM_SYMBOLS       = 389,
      START_STATE       = 1391,
      EOFT_SYMBOL       = 59,
      EOLT_SYMBOL       = 59,
      ACCEPT_ACTION     = 9950,
      ERROR_ACTION      = 9951;
}
