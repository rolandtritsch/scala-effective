import codecs.*
import codecs.Util.*

renderJson(())
renderJson(42)
renderJson("roland") 
renderJson(true)

parseJson("null")
parseJson("42")
parseJson("4.x")
parseJson("\"roland\"")
parseJson("true")

renderJson(Person("Bob", 66))
parseJson("""{"name":"Roland", "age":55}""")

val maybeJsonString = parseJson(""" "foo" """)
val maybeJsonObj = parseJson(""" { "name": "Alice", "age": 42 } """)
val maybeJsonObj2 = parseJson(""" { "name": "Alice", "age": "42" } """)

maybeJsonString.flatMap(_.decodeAs[Int])
maybeJsonString.flatMap(_.decodeAs[String])
maybeJsonObj.flatMap(_.decodeAs[Person]) 
maybeJsonObj2.flatMap(_.decodeAs[Person])
