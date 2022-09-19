import com.plainconcepts.hello.common.LanguageDTO
import components.AddLanguageForm
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
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.p

private val scope = MainScope()

val App = FC<Props> {
    var hello by useState("")
    var showForm by useState(false)
    var languages by useState(emptyList<LanguageDTO>())

    val api = API()

    useEffectOnce {
        scope.launch {
            languages = api.getLanguages()
            hello = api.getHello()
        }
    }

    val openForm = useCallback { showForm = true }
    val closeForm = useCallback { showForm = false }

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
                margin = Margin(35.vh, Auto.auto, 0.px)
                flex = Flex(1.unsafeCast<FlexGrow>(), 0.unsafeCast<FlexShrink>(), Auto.auto)
            }

            +hello
        }

        footer {
            className = ClassName("options")

            css {
                width = 100.pct
                height = 30.vh
                padding = Padding(2.rem, 0.rem)
                backgroundColor = Color("#CBF9DD")
                boxShadow = BoxShadow(0.px, (-5).px, 5.px, Color("#87E3AB"))
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
                    languages.forEach {
                        option {
                            value = it.code
                            +it.name
                        }
                    }
                }
            }

            div {
                css {
                    marginTop = 2.rem
                }

                h2 {
                    if (showForm) +"Teach me a new language ▲"
                    if (!showForm) +"Teach me a new language ▼"
                    onClick = { if (showForm) closeForm() else openForm() }
                }

                if (showForm) AddLanguageForm {
                    submit = {
                        scope.launch {
                            api.upsertHello(it.code, it.name, it.hello)
                            languages = api.getLanguages()
                            hello = api.getHello(it.code)
                        }
                    }
                    close = closeForm
                }
            }
        }
    }
}