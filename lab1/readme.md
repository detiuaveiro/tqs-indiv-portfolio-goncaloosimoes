# 1.1 - Alínea (e)

13/11 é um número infinito, por isso a asserção falha. Para resolver esse problema usamos uma tolerância de diferença que caso seja aceite passa no teste por a margem de erro ser mínima.

# 1.2 - Alínea (i)

Apesar dos testes realizados com sucesso não temos ainda 100% de garantias que a stack nunca falhe. Pode existir acesso concorrido e como a stack ainda não é thread safe operações simultâneas podem comprometer o bom tratamento dos dados.
Problemas de memória: sem gestão de limites de memória o push de muitos elementos pode causar erros.
Casos limite de funções que usem inteiros como a popTopN: podem ocorrer overflows de inteiros não testados.

# 1.3 - Alínea (d)

Áreas com menos abrangência de testes: métodos time-based como isTooLateToBook() e isTooLateToCancel(), são mais difíceis de testar devido a dependências de tempo. Edge cases de validação: um estudante bloquear após múltiplas ausências.

### Missing Branch Coverage:
Booking Validation:

Not all combinations of validation failures
Concurrent booking scenarios
Cancellation Logic:

Some state transitions
Time-based conditions

### To improve coverage:

Mock time dependencies

Add more edge case tests

Test concurrent scenarios

Add state transition tests

Test capacity management thoroughly

Nota: 100% coverage não implica bug-free code, mas ajuda a identificar cenários não testados previamente e reduz o risco de erros.