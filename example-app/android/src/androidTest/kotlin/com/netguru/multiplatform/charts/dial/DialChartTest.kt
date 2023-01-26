package com.netguru.multiplatform.charts.dial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.karumi.shot.ScreenshotTest
import com.netguru.multiplatform.charts.ChartDisplayAnimation
import com.netguru.multiplatform.charts.Util.checkComposable
import com.netguru.multiplatform.charts.dial.scale.MarkType
import com.netguru.multiplatform.charts.dial.scale.ScaleConfig
import com.netguru.multiplatform.charts.line.Progression
import org.junit.Rule
import org.junit.Test

class DialChartTest : ScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun range0To100_value50_joinJoined_roundCorners_customThickness() {
        checkComposable(composeRule) {
            Dial(
                value = 50f,
                animation = ChartDisplayAnimation.Disabled,
                config = DialConfig(
                    joinStyle = DialJoinStyle.Joined,
                    roundCorners = true,
                    thickness = 25.dp,
                ),
                minAndMaxValueLabel = null,
                mainLabel = null,
                scaleConfig = null,
            )
        }
    }

    @Test
    fun rangeMinus20To80_value50_joinJoined_roundCorners_defaultUI() {
        checkComposable(composeRule) {
            Dial(
                value = 50f,
                minValue = -20f,
                maxValue = 80f,
                animation = ChartDisplayAnimation.Disabled,
                config = DialConfig(
                    joinStyle = DialJoinStyle.Joined,
                    roundCorners = true,
                ),
            )
        }
    }

    @Test
    fun range0To100_value0_joinJoined_roundCorners_customThickness() {
        checkComposable(composeRule) {
            Dial(
                value = 0f,
                animation = ChartDisplayAnimation.Disabled,
                config = DialConfig(
                    joinStyle = DialJoinStyle.Joined,
                    roundCorners = true,
                    thickness = 25.dp,
                ),
                minAndMaxValueLabel = null,
                mainLabel = null,
                scaleConfig = null,
            )
        }
    }

    @Test
    fun range0To100_value100_joinJoined_roundCorners_customThickness() {
        checkComposable(composeRule) {
            Dial(
                value = 100f,
                animation = ChartDisplayAnimation.Disabled,
                config = DialConfig(
                    joinStyle = DialJoinStyle.Joined,
                    roundCorners = true,
                    thickness = 25.dp,
                ),
                minAndMaxValueLabel = null,
                mainLabel = null,
                scaleConfig = null,
            )
        }
    }

    @Test
    fun range0To100_value50_joinJoined_squareCorners_customMinMaxLabel_defaultMainLabel() {
        checkComposable(composeRule) {
            Dial(
                value = 50f,
                animation = ChartDisplayAnimation.Disabled,
                config = DialConfig(
                    joinStyle = DialJoinStyle.Joined,
                    roundCorners = false,
                ),
                minAndMaxValueLabel = {
                    Text(text = it.toString())
                },
                scaleConfig = null,
            )
        }
    }

    @Test
    fun range0To100_value0_joinJoined_squareCorners_customMinMaxLabel_defaultMainLabel_customizedLinearScaleWithLines() {
        checkComposable(composeRule) {
            Dial(
                value = 0f,
                animation = ChartDisplayAnimation.Disabled,
                config = DialConfig(
                    joinStyle = DialJoinStyle.Joined,
                    roundCorners = false,
                ),
                minAndMaxValueLabel = {
                    Text(text = it.toString())
                },
                scaleConfig = ScaleConfig.LinearProgressionConfig(
                    scalePadding = 10.dp,
                    scaleLineWidth = 4.dp,
                    scaleLineLength = 26.dp,
                    scaleLabelLayout = {
                        Text(text = it.toString())
                    },
                    markType = MarkType.Line,
                    smallMarkStep = 1f,
                    bigMarkStep = 5f,
                ),
            )
        }
    }

    @Test
    fun range0To100_value100_joinJoined_squareCorners_customMinMaxLabel_defaultMainLabel_defaultLinearScaleWithDots() {
        checkComposable(composeRule) {
            Dial(
                value = 100f,
                animation = ChartDisplayAnimation.Disabled,
                config = DialConfig(
                    joinStyle = DialJoinStyle.Joined,
                    roundCorners = false,
                ),
                minAndMaxValueLabel = {
                    Text(text = it.toString())
                },
                scaleConfig = ScaleConfig.LinearProgressionConfig(
                    scaleLabelLayout = {
                        Text(text = it.toString())
                    },
                    markType = MarkType.Dot,
                ),
            )
        }
    }

    @Test
    fun range0To100_value0_joinJoined_squareCorners_customMinMaxLabel_defaultMainLabel_customizedLinearScaleWithLinesWithoutLabels() {
        checkComposable(composeRule) {
            Dial(
                value = 0f,
                animation = ChartDisplayAnimation.Disabled,
                config = DialConfig(
                    joinStyle = DialJoinStyle.Joined,
                    roundCorners = false,
                ),
                minAndMaxValueLabel = {
                    Text(text = it.toString())
                },
                scaleConfig = ScaleConfig.LinearProgressionConfig(
                    scalePadding = 10.dp,
                    scaleLineWidth = 4.dp,
                    scaleLineLength = 26.dp,
                    scaleLabelLayout = null,
                    markType = MarkType.Line,
                    smallMarkStep = 1f,
                    bigMarkStep = 5f,
                ),
            )
        }
    }

    @Test
    fun range0To100_value100_joinJoined_squareCorners_customMinMaxLabel_defaultMainLabel_customizedLinearScaleWithDotsWithoutLabels() {
        checkComposable(composeRule) {
            Dial(
                value = 100f,
                animation = ChartDisplayAnimation.Disabled,
                config = DialConfig(
                    joinStyle = DialJoinStyle.Joined,
                    roundCorners = false,
                ),
                minAndMaxValueLabel = {
                    Text(text = it.toString())
                },
                scaleConfig = ScaleConfig.LinearProgressionConfig(
                    scalePadding = 10.dp,
                    scaleLineWidth = 4.dp,
                    scaleLineLength = 26.dp,
                    scaleLabelLayout = null,
                    markType = MarkType.Dot,
                    smallMarkStep = 1f,
                    bigMarkStep = 5f,
                ),
            )
        }
    }

    @Test
    fun range0To100_value50_joinWithDegreeGap5_squareCorners_customMinMaxLabel_defaultMainLabel_indicatorArrow() {
        checkComposable(composeRule) {
            Dial(
                value = 50f,
                animation = ChartDisplayAnimation.Disabled,
                config = DialConfig(
                    joinStyle = DialJoinStyle.WithDegreeGap(5f),
                    roundCorners = false,
                ),
                minAndMaxValueLabel = {
                    Text(text = it.toString())
                },
                scaleConfig = null,
                indicator = {
                    Image(
                        painter = rememberVectorPainter(image = Arrow),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(
                                start = 20.dp,
                            )
                            .rotate(-90f)
                    )
                },
            )
        }
    }

    @Test
    fun range0To100_value100_joinOverlapped_roundCorners_noMinMaxLabel_defaultMainLabel_indicatorLine_nonLinearProgression_gradientWithStops() {
        checkComposable(composeRule) {
            Dial(
                value = 100f,
                animation = ChartDisplayAnimation.Disabled,
                config = DialConfig(
                    joinStyle = DialJoinStyle.Overlapped,
                    roundCorners = true,
                    fullAngleInDegrees = 270f,
                ),
                minAndMaxValueLabel = null,
                scaleConfig = null,
                progression = Progression.NonLinear(
                    anchorPoints = listOf(
                        Progression.NonLinear.AnchorPoint(0f, 0f),
                        Progression.NonLinear.AnchorPoint(20f, 0.1f),
                        Progression.NonLinear.AnchorPoint(40f, 0.2f),
                        Progression.NonLinear.AnchorPoint(60f, 0.3f),
                        Progression.NonLinear.AnchorPoint(80f, 0.8f),
                        Progression.NonLinear.AnchorPoint(90f, 0.9f),
                        Progression.NonLinear.AnchorPoint(100f, 1f),
                    )
                ),
                indicator = {
                    Box(
                        modifier = Modifier
                            .background(Color.Red)
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                },
                colors = DialChartDefaults.dialChartColors(
                    progressBarColor = DialProgressColors.GradientWithStops(
                        listOf(
                            0f to Color.Yellow,
                            0.2f to Color.Blue,
                            0.8f to Color.Red,
                            1f to Color.Green,
                        )
                    )
                )
            )
        }
    }

    @Test
    fun range0To100_value100_joinOverlapped_roundCorners_noMinMaxLabel_defaultMainLabel_indicatorLine_nonLinearProgression_gradient() {
        checkComposable(composeRule) {
            Dial(
                value = 100f,
                animation = ChartDisplayAnimation.Disabled,
                config = DialConfig(
                    joinStyle = DialJoinStyle.Overlapped,
                    roundCorners = true,
                    fullAngleInDegrees = 270f,
                ),
                minAndMaxValueLabel = null,
                scaleConfig = null,
                progression = Progression.NonLinear(
                    anchorPoints = listOf(
                        Progression.NonLinear.AnchorPoint(0f, 0f),
                        Progression.NonLinear.AnchorPoint(20f, 0.1f),
                        Progression.NonLinear.AnchorPoint(40f, 0.2f),
                        Progression.NonLinear.AnchorPoint(60f, 0.3f),
                        Progression.NonLinear.AnchorPoint(80f, 0.8f),
                        Progression.NonLinear.AnchorPoint(90f, 0.9f),
                        Progression.NonLinear.AnchorPoint(100f, 1f),
                    )
                ),
                indicator = {
                    Box(
                        modifier = Modifier
                            .background(Color.Red)
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                },
                colors = DialChartDefaults.dialChartColors(
                    progressBarColor = DialProgressColors.Gradient(
                        listOf(
                            Color.Yellow,
                            Color.Blue,
                            Color.Red,
                            Color.Green,
                        )
                    )
                )
            )
        }
    }
}
