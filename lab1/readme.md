1.1 - Alínea (e)

13/11 é um número infinito, por isso a asserção falha. Para resolver esse problema usamos uma tolerância de diferença que caso seja aceite passa no teste por a margem de erro ser mínima.

1.2 - Alínea (i)

Apesar dos testes realizados com sucesso não temos ainda 100% de garantias que a stack nunca falhe. Pode existir acesso concorrido e como a stack ainda não é thread safe operações simultâneas podem comprometer o bom tratamento dos dados.
Problemas de memória: sem gestão de limites de memória o push de muitos elementos pode causar erros.
Casos limite de funções que usem inteiros como a popTopN: podem ocorrer overflows de inteiros não testados.