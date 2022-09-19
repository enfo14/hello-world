package components

import com.plainconcepts.hello.common.LanguageRequest
import csstype.*
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ButtonType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.useState

external interface ModalProps : Props {
    var submit: (form: LanguageRequest) -> Unit
    var close: () -> Unit
}

val AddLanguageForm = FC<ModalProps> { props ->
    var code by useState("")
    var lang by useState("")
    var hello by useState("")

    form {
        css {
            margin = Margin(0.rem, Auto.auto)
            display = Display.flex
            flexDirection = FlexDirection.column
            marginTop = 1.rem
        }

        onSubmit = {
            props.submit(LanguageRequest(code, lang, hello))
            props.close()
        }

        onReset = {
            code = ""
            lang = ""
            hello = ""
            props.close()
        }

        label {
            css { marginTop = 1.rem }
            +"Language code"
            htmlFor = "code"

            input {
                css { marginLeft = 1.rem }
                name = "code"
                minLength = 2
                maxLength = 2

                onChange = { code = it.target.value }
            }
        }

        label {
            css { marginTop = 1.rem }
            +"Language"
            htmlFor = "name"

            input {
                css { marginLeft = 1.rem }
                name = "name"
                maxLength = 50

                onChange = { lang = it.target.value }
            }
        }

        label {
            css { marginTop = 1.rem }
            +"Hello, world!"
            htmlFor = "hello"

            input {
                css { marginLeft = 1.rem }
                name = "hello"
                onChange = { hello = it.target.value }
            }
        }

        div {
            css {
                display = Display.flex
                justifyContent = JustifyContent.center
                marginTop = 1.rem
            }

            button {
                +"Cancel"
                type = ButtonType.reset
            }

            button {
                css { marginLeft = 1.rem }
                +"Save"
                type = ButtonType.submit
            }
        }
    }
}