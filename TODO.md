## ✅ TODO List Completo (Backend + Lógica de IA + CRUDs)

---

### 1. ✅ **Modelagem de Entidades**

* [ ] Criar entidade `PersonalQuest` com:

  * `id`, `userId`, `name`, `domain`, `description`, `weeklyFrequency`, `startDate?`, `endDate?`, `metricType`, `completed`
* [ ] Criar entidade `ChallengeTrail`:

  * `id`, `quest`, `days: List<ChallengeDay>`
* [ ] Criar entidade `ChallengeDay`:

  * `date`, `steps: List<ChallengeStep>`
* [ ] Criar entidade `ChallengeStep`:

  * `description`, `completed`

---

### 2. 🛠️ **CRUDs e Endpoints**

#### 🔹 PersonalQuest

* [ ] Criar nova `quest`
* [ ] Editar `quest` (nome, descrição, domínio, frequência, datas, completude)
* [ ] Excluir `quest`
* [ ] Listar todas as `quests` do usuário
* [ ] Ver detalhes de uma `quest`, incluindo sua `ChallengeTrail`

#### 🔹 ChallengeTrail

* [ ] Gerar trail automaticamente (IA)
* [ ] Atualizar dias da semana e frequência
* [ ] Regerar trail se `weeklyFrequency`, `startDate` ou `endDate` mudar
* [ ] Permitir edição manual da trail pelo usuário

#### 🔹 ChallengeDay

* [ ] Visualizar lista de dias com tarefas
* [ ] Adicionar ou excluir dias específicos (exceções)
* [ ] Editar data de um dia

#### 🔹 ChallengeStep

* [ ] Criar passo manualmente
* [ ] Editar passo (descrição)
* [ ] Marcar passo como concluído ou não
* [ ] Excluir passo
* [ ] Mover passo entre dias

---

### 3. 🤖 **Lógica de IA e Regras de Geração**

* [ ] Dado um `PersonalQuest`, gerar a `ChallengeTrail` com base em:

  * `startDate`, `endDate`, `weeklyFrequency`, `metricType`
* [ ] Calcular número total de semanas
* [ ] Gerar os dias (`ChallengeDay`) da primeira semana, e seus `steps` com base no domínio + métrica
* [ ] Reutilizar estrutura de `ChallengeStep` nas demais semanas
* [ ] Avaliar completude com base nas métricas (`última métrica do tipo associado ao quest`)

---

### 4. 📈 **Lógica de Métricas**

* [ ] Validar se uma `ChallengeStep` pode ser marcada como concluída automaticamente com base na métrica (`AbstractMetricRecord`)
* [ ] Registrar métricas automaticamente quando o usuário completa um passo, se aplicável

---

### 5. 🔐 **Permissões e Segurança**

* [ ] Garantir que o usuário só pode acessar suas próprias `quests`, `trails`, `steps`
* [ ] Bloquear edição de trilhas completadas (se necessário)

---

### 6. 🖥️ **Interface (caso frontend)**

* [ ] Tela para criação de PersonalQuest
* [ ] Calendário semanal com ChallengeDays
* [ ] Checklists para cada ChallengeDay
* [ ] Histórico de progresso
* [ ] Feedback visual com base nas métricas

---

## 📌 Sugestão de ordem de implementação

1. CRUD básico de `PersonalQuest`
2. Serviço de geração de `ChallengeTrail`
3. Lógica de repetição automática de `ChallengeDay`
4. CRUD de `ChallengeDay` e `ChallengeStep`
5. Integração com `AbstractMetricRecord`
6. Conclusão automática de steps via IA