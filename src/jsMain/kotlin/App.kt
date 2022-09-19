import csstype.*
import emotion.react.css
import infrastructure.API
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.footer
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.p

private val scope = MainScope()

private data class Language(val code: String, val name: String)

val App = FC<Props> {
    var hello by useState("")

    useEffectOnce {
        scope.launch {
            hello = API().getHello()
        }
    }

    val options = listOf(
        Language("en", "English"),
        Language("es", "Español"),
        Language("ca", "Català"),
        Language("eu", "Euskara"),
        Language("jp", "日本語"),
    )

    div {
        css {
            width = 100.vw
            minHeight = 100.vh
            display = Display.flex
            flexDirection = FlexDirection.column
            backgroundColor = Color("#D0F4DE")
            focus {
                outline = "none".unsafeCast<Outline>()
            }
        }
        tabIndex = 0

        onKeyUp = {
            if (it.key == " ") {
                scope.launch { hello = API().getHello() }
            }
        }

        h1 {
            css {
                fontSize = 120.px
                fontWeight = FontWeight.bold
                margin = Margin(45.vh, Auto.auto, 0.px)
                flex = Flex(1.unsafeCast<FlexGrow>(), 0.unsafeCast<FlexShrink>(), Auto.auto)
            }

            +hello
        }

        footer {
            className = ClassName("options")

            css {
                width = 100.pct
                height = 20.vh
                boxShadow = BoxShadow(0.px, (-5).px, 5.px, Color("#CBF9DD"))
                textAlign = TextAlign.center
            }

            p {
                +"Press the space bar to get a greeting in a random language"
            }

            label {
                css {
                    display = Display.block
                    marginTop = 1.rem
                }

                htmlFor = "lang"
                +"If you prefer, you can also choose a language"

                ReactHTML.select {
                    css {
                        marginLeft = 1.rem
                    }

                    onChange = {
                        scope.launch { hello = API().getHello(it.target.value) }
                    }

                    name = "lang"
                    options.forEach {
                        option {
                            value = it.code
                            +it.name
                        }
                    }
                }
            }
        }
    }
}