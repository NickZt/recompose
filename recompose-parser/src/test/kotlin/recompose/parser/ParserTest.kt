/*
 * Copyright 2020 Sebastian Kaspari
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package recompose.parser

import com.jds.recompose.attributes.ViewAttributes
import com.jds.recompose.attributes.ViewGroupAttributes
import com.jds.recompose.nodes.*
import com.jds.recompose.values.*
import org.junit.Test
import recompose.test.utils.assertAST

class ParserTest {
    @Test
    fun `LinearLayout with TextView and Button`() {
        assertAST(
            fileName = "linearlayout-textview-button.xml",
            expected = Layout(
                listOf(
                    LinearLayoutNode(
                        ViewAttributes(
                            width = LayoutSize.MatchParent,
                            height = LayoutSize.MatchParent
                        ),
                        ViewGroupNode(
                            view = ViewAttributes(
                                width = LayoutSize.MatchParent,
                                height = LayoutSize.MatchParent
                            ),
                            viewGroupAttributes = ViewGroupAttributes(
                                listOf(
                                    TextViewNode(
                                        ViewAttributes(
                                            width = LayoutSize.WrapContent,
                                            height = LayoutSize.WrapContent
                                        ),
                                        text = "Hello World!",
                                        textColor = Color.Absolute(0xFFFF0000)
                                    ),
                                    ButtonNode(
                                        ViewAttributes(
                                            width = LayoutSize.WrapContent,
                                            height = LayoutSize.WrapContent
                                        ),
                                        text = "Click me!"
                                    )
                                )
                            )
                        ),
                        Orientation.Vertical
                    )
                )
            )
        )
    }

    @Test
    fun `TextView with absolute dp sizes`() {
        assertAST(
            "textview-absolute-dp-sizes.xml",
            Layout(
                listOf(
                    TextViewNode(
                        ViewAttributes(
                            width = LayoutSize.Absolute(Size.Dp(100)),
                            height = LayoutSize.Absolute(Size.Dp(50))
                        ),
                        text = "I am a test",
                        textColor = null
                    )
                )
            )
        )
    }

    @Test
    fun `ConstraintLayout with Buttons`() {
        assertAST(
            fileName = "constraintlayout-buttons.xml",
            Layout(
                listOf(
                    ConstraintLayoutNode(
                        ViewAttributes(
                            width = LayoutSize.MatchParent,
                            height = LayoutSize.MatchParent
                        ),
                        ViewGroupNode(
                            view = ViewAttributes(
                                width = LayoutSize.MatchParent,
                                height = LayoutSize.MatchParent
                            ),
                            ViewGroupAttributes(
                                children = listOf(
                                    ButtonNode(
                                        ViewAttributes(
                                            id = Id("button000"),
                                            width = LayoutSize.WrapContent,
                                            height = LayoutSize.WrapContent,
                                            constraints = Constraints(
                                                Constraints.RelativePositioning(
                                                    startToStart = Constraints.Id.Parent,
                                                    topToTop = Constraints.Id.Parent
                                                )
                                            )
                                        ),
                                        text = "000"
                                    ),
                                    ButtonNode(
                                        ViewAttributes(
                                            id = Id("button001"),
                                            width = LayoutSize.WrapContent,
                                            height = LayoutSize.WrapContent,
                                            constraints = Constraints(
                                                Constraints.RelativePositioning(
                                                    endToEnd = Constraints.Id.Parent,
                                                    startToStart = Constraints.Id.Parent,
                                                    topToBottom = Constraints.Id.View("button000")
                                                )
                                            )
                                        ),
                                        text = "001"
                                    ),
                                    ButtonNode(
                                        ViewAttributes(
                                            width = LayoutSize.Absolute(Size.Dp(0)),
                                            height = LayoutSize.WrapContent,
                                            constraints = Constraints(
                                                Constraints.RelativePositioning(
                                                    bottomToBottom = Constraints.Id.Parent,
                                                    endToEnd = Constraints.Id.Parent,
                                                    startToStart = Constraints.Id.Parent,
                                                    topToBottom = Constraints.Id.View("button001")
                                                )
                                            )
                                        ),
                                        text = "010"
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `TextView with attributes`() {
        assertAST(
            fileName = "textview-attributes.xml",
            Layout(
                children = listOf(
                    TextViewNode(
                        view = ViewAttributes(
                            id = Id("title"),
                            width = LayoutSize.Absolute(Size.Dp(100)),
                            height = LayoutSize.WrapContent,
                            background = Drawable.ColorValue(Color.Absolute(0xAA0000FF))
                        ),
                        text = "I am a test",
                        textSize = Size.Sp(20),
                        textColor = Color.Absolute(0xFFFFCC00),
                        maxLines = 100
                    )
                )
            )
        )
    }

    @Test
    fun `TextViews with different padding configurations`() {
        assertAST(
            fileName = "textview-padding.xml",
            Layout(
                listOf(
                    LinearLayoutNode(
                        view = ViewAttributes(
                            width = LayoutSize.MatchParent,
                            height = LayoutSize.MatchParent
                        ),
                        orientation = Orientation.Vertical,
                        viewGroup =

                        ViewGroupNode(
                            view = ViewAttributes(
                                width = LayoutSize.MatchParent,
                                height = LayoutSize.MatchParent
                            ),
                            ViewGroupAttributes(
                                listOf(
                                    TextViewNode(
                                        view = ViewAttributes(
                                            width = LayoutSize.WrapContent,
                                            height = LayoutSize.WrapContent,
                                            background = Drawable.ColorValue(Color.Absolute(0xffff0000)),
                                            padding = Padding(all = Size.Dp(10))
                                        ),
                                        text = "padding"
                                    ),
                                    TextViewNode(
                                        view = ViewAttributes(
                                            width = LayoutSize.WrapContent,
                                            height = LayoutSize.WrapContent,
                                            background = Drawable.ColorValue(Color.Absolute(0xff00ff00)),
                                            padding = Padding(top = Size.Dp(10), left = Size.Dp(10))
                                        ),
                                        text = "paddingTop/Left"
                                    ),
                                    TextViewNode(
                                        view = ViewAttributes(
                                            width = LayoutSize.WrapContent,
                                            height = LayoutSize.WrapContent,
                                            background = Drawable.ColorValue(Color.Absolute(0xff0000ff)),
                                            padding = Padding(right = Size.Dp(10), bottom = Size.Dp(10))
                                        ),
                                        text = "paddingRight/Bottom"
                                    ),
                                    TextViewNode(
                                        view = ViewAttributes(
                                            width = LayoutSize.WrapContent,
                                            height = LayoutSize.WrapContent,
                                            background = Drawable.ColorValue(Color.Absolute(0xff00ffff)),
                                            padding = Padding(start = Size.Dp(10), end = Size.Dp(10))
                                        ),
                                        text = "paddingStartEnd"
                                    ),
                                    TextViewNode(
                                        view = ViewAttributes(
                                            width = LayoutSize.WrapContent,
                                            height = LayoutSize.WrapContent,
                                            background = Drawable.ColorValue(Color.Absolute(0xffff00ff)),
                                            padding = Padding(horizontal = Size.Dp(10), vertical = Size.Dp(10))
                                        ),
                                        text = "paddingHorizontalVertically"
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `Basic Views`() {
        assertAST(
            fileName = "view.xml",
            Layout(
                children = listOf(
                    LinearLayoutNode(
                        view = ViewAttributes(
                            width = LayoutSize.MatchParent,
                            height = LayoutSize.MatchParent
                        ),
                        orientation = Orientation.Horizontal,
                        viewGroup = ViewGroupNode(
                            view = ViewAttributes(
                                width = LayoutSize.MatchParent,
                                height = LayoutSize.MatchParent
                            ),
                            ViewGroupAttributes(
                                listOf(
                                    ViewNode(
                                        view = ViewAttributes(
                                            id = Id("one"),
                                            width = LayoutSize.Absolute(Size.Dp(50)),
                                            height = LayoutSize.Absolute(Size.Dp(50)),
                                            background = Drawable.ColorValue(Color.Absolute(0xffff0000))
                                        )
                                    ),
                                    ViewNode(
                                        view = ViewAttributes(
                                            id = Id("two"),
                                            width = LayoutSize.Absolute(Size.Dp(50)),
                                            height = LayoutSize.Absolute(Size.Dp(50)),
                                            background = Drawable.ColorValue(Color.Absolute(0xff00ff00))
                                        )
                                    ),
                                    ViewNode(
                                        view = ViewAttributes(
                                            id = Id("three"),
                                            width = LayoutSize.Absolute(Size.Dp(50)),
                                            height = LayoutSize.Absolute(Size.Dp(50)),
                                            background = Drawable.ColorValue(Color.Absolute(0xff0000ff))
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `Basic ImageView`() {
        assertAST(
            fileName = "imageview.xml",
            Layout(
                children = listOf(
                    ImageViewNode(
                        view = ViewAttributes(
                            id = Id("powerOff"),
                            width = LayoutSize.Absolute(Size.Dp(100)),
                            height = LayoutSize.Absolute(Size.Dp(100)),
                        ),
                        src = Drawable.Resource("ic_lock_power_off")
                    )
                )
            )
        )
    }

    @Test
    fun `Simple CardView`() {
        assertAST(
            fileName = "cardview.xml",
            Layout(
                children = listOf(
                    CardViewNode(
                        view = ViewAttributes(
                            id = Id("card"),
                            width = LayoutSize.Absolute(Size.Dp(300)),
                            height = LayoutSize.Absolute(Size.Dp(100)),
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `CardView with TextView and Button`() {
        assertAST(
            fileName = "cardview-textview-button.xml",
            expected = Layout(
                children = listOf(
                    CardViewNode(
                        view = ViewAttributes(
                            id = Id("card"),
                            width = LayoutSize.Absolute(Size.Dp(300)),
                            height = LayoutSize.Absolute(Size.Dp(100)),
                        ),
                        viewGroup = ViewGroupNode(
                            view = ViewAttributes(
                                width = LayoutSize.MatchParent,
                                height = LayoutSize.MatchParent
                            ),
                            ViewGroupAttributes(
                                listOf(
                                    LinearLayoutNode(
                                        view = ViewAttributes(
                                            width = LayoutSize.MatchParent,
                                            height = LayoutSize.MatchParent
                                        ),
                                        viewGroup = ViewGroupNode(
                                            view = ViewAttributes(
                                                width = LayoutSize.MatchParent,
                                                height = LayoutSize.MatchParent
                                            ),
                                            ViewGroupAttributes(
                                                listOf(
                                                    TextViewNode(
                                                        ViewAttributes(
                                                            width = LayoutSize.WrapContent,
                                                            height = LayoutSize.WrapContent
                                                        ),
                                                        text = "Hello World!",
                                                        textColor = Color.Absolute(0xFFFF0000)
                                                    ),
                                                    ButtonNode(
                                                        ViewAttributes(
                                                            width = LayoutSize.WrapContent,
                                                            height = LayoutSize.WrapContent
                                                        ),
                                                        text = "Click me!"
                                                    )
                                                )
                                            )
                                        ),
                                        Orientation.Vertical
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `Simple MaterialCardView`() {
        assertAST(
            fileName = "material-cardview.xml",
            Layout(
                children = listOf(
                    CardViewNode(
                        view = ViewAttributes(
                            id = Id("card"),
                            width = LayoutSize.Absolute(Size.Dp(300)),
                            height = LayoutSize.Absolute(Size.Dp(100)),
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `MaterialCardView with TextView and Button`() {
        assertAST(
            fileName = "material-cardview-textview-button.xml",
            expected = Layout(
                children = listOf(
                    CardViewNode(
                        view = ViewAttributes(
                            id = Id("card"),
                            width = LayoutSize.Absolute(Size.Dp(300)),
                            height = LayoutSize.Absolute(Size.Dp(100)),
                        ),
                        viewGroup = ViewGroupNode(
                            view = ViewAttributes(
                                width = LayoutSize.MatchParent,
                                height = LayoutSize.MatchParent
                            ),
                            ViewGroupAttributes(
                                children = listOf(
                                    LinearLayoutNode(
                                        ViewAttributes(
                                            width = LayoutSize.MatchParent,
                                            height = LayoutSize.MatchParent
                                        ),
                                        ViewGroupNode(
                                            view = ViewAttributes(
                                                width = LayoutSize.MatchParent,
                                                height = LayoutSize.MatchParent
                                            ),
                                            ViewGroupAttributes(
                                                listOf(
                                                    TextViewNode(
                                                        ViewAttributes(
                                                            width = LayoutSize.WrapContent,
                                                            height = LayoutSize.WrapContent
                                                        ),
                                                        text = "Hello World!",
                                                        textColor = Color.Absolute(0xFFFF0000)
                                                    ),
                                                    ButtonNode(
                                                        ViewAttributes(
                                                            width = LayoutSize.WrapContent,
                                                            height = LayoutSize.WrapContent
                                                        ),
                                                        text = "Click me!"
                                                    )
                                                )
                                            )
                                        ),
                                        orientation = Orientation.Vertical
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `Basic Checkbox`() {
        assertAST(
            "checkbox.xml",
            Layout(
                listOf(
                    CheckBoxNode(
                        view = ViewAttributes(
                            width = LayoutSize.MatchParent,
                            height = LayoutSize.WrapContent
                        ),
                        text = "Hello World",
                        checked = true
                    )
                )
            )
        )
    }

    @Test
    fun `Basic EditText`() {
        assertAST(
            "edittext.xml",
            Layout(
                listOf(
                    EditTextNode(
                        view = ViewAttributes(
                            width = LayoutSize.Absolute(Size.Dp(200)),
                            height = LayoutSize.Absolute(Size.Dp(50))
                        ),
                        text = "",
                        inputType = InputType.Text,
                        hint = ""
                    )
                )
            )
        )
    }

    @Test
    fun `Basic EditText with hint`() {
        assertAST(
            "edittext-hint.xml",
            Layout(
                listOf(
                    EditTextNode(
                        view = ViewAttributes(
                            width = LayoutSize.Absolute(Size.Dp(200)),
                            height = LayoutSize.Absolute(Size.Dp(50))
                        ),
                        text = "",
                        inputType = InputType.Text,
                        hint = "text",
                        textColorHint = Color.Resource("green")
                    )
                )
            )
        )
    }

    @Test
    fun `FrameLayout with TextView and Button`() {
        assertAST(
            fileName = "framelayout-textview-button.xml",
            Layout(
                listOf(
                    FrameLayoutNode(
                        view = ViewAttributes(
                            width = LayoutSize.MatchParent,
                            height = LayoutSize.MatchParent,
                            padding = Padding(all = Size.Dp(16))
                        ),
                        viewGroup = ViewGroupNode(
                            view = ViewAttributes(
                                width = LayoutSize.MatchParent,
                                height = LayoutSize.MatchParent
                            ),
                            ViewGroupAttributes(
                                children = listOf(
                                    TextViewNode(
                                        view = ViewAttributes(
                                            width = LayoutSize.MatchParent,
                                            height = LayoutSize.MatchParent
                                        ),
                                        text = "Center",
                                        textSize = Size.Sp(20)
                                    ),
                                    ButtonNode(
                                        view = ViewAttributes(
                                            width = LayoutSize.MatchParent,
                                            height = LayoutSize.WrapContent
                                        ),
                                        text = "Button"
                                    )
                                )

                            )
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `Basic Style Attributes`() {
        assertAST(
            "styleattributes.xml",
            Layout(
                listOf(
                    TextViewNode(
                        view = ViewAttributes(
                            width = LayoutSize.Absolute(Size.Dp(200)),
                            height = LayoutSize.Absolute(Size.Dp(50)),
                            background = Drawable.StyleAttribute("selectableItemBackground")
                        ),
                        text = "",
                    )
                )
            )
        )
    }

    @Test
    fun `Basic Switch`() {
        assertAST(
            "switch.xml",
            Layout(
                listOf(
                    SwitchNode(
                        view = ViewAttributes(
                            width = LayoutSize.MatchParent,
                            height = LayoutSize.MatchParent
                        ),
                        checked = true
                    )
                )
            )
        )
    }
}
