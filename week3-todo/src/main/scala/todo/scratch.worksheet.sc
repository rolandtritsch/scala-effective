import todo.*
import todo.data.*

val tasks = InMemoryModel.tasks
val firstTask = InMemoryModel.read(Id(0))
val activeTask = InMemoryModel.read(Id(1))
val completedTask = activeTask.flatMap(t => Option(t.copy(state = State.completedNow)))
