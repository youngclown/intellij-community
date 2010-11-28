package com.jetbrains.python.actions;

import com.intellij.codeInsight.CodeInsightUtilBase;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.jetbrains.python.PyBundle;
import com.jetbrains.python.psi.*;
import org.jetbrains.annotations.NotNull;

/**
 * User: catherine
 *
 * QuickFix to add self to unresolved reference
 */
public class UnresolvedReferenceAddSelfQuickFix implements LocalQuickFix {
  private PyReferenceExpression myElement;

  public UnresolvedReferenceAddSelfQuickFix(PyReferenceExpression element) {
    myElement = element;
  }

  @NotNull
  public String getName() {
    return PyBundle.message("QFIX.unresolved.reference");
  }

  @NotNull
  public String getFamilyName() {
    return PyBundle.message("INSP.GROUP.python");
  }

  public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
      if (!CodeInsightUtilBase.preparePsiElementForWrite(myElement)) return;
      PyElementGenerator elementGenerator = PyElementGenerator.getInstance(project);
      PyExpression expression = elementGenerator.createExpressionFromText("self." + myElement.getText());
      myElement.replace(expression);
  }
}
