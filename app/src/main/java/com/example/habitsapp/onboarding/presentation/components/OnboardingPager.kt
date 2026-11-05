package com.example.habitsapp.onboarding.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.habitsapp.core.presentation.HabitsButton
import com.example.habitsapp.core.presentation.HabitsTitle
import com.example.habitsapp.onboarding.presentation.OnboardingPagerInfo
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingPager(
    modifier: Modifier = Modifier,
    pages: List<OnboardingPagerInfo> = listOf(),
    onFinish: () -> Unit = {}
) {
    Box(modifier = modifier.background(color = Color.White)) {
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()
        HorizontalPager(count = pages.size, state = pagerState) { index ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.padding(32.dp))
                HabitsTitle(pages[index].title)
                Spacer(modifier = Modifier.padding(32.dp))
                Image(
                    painter = painterResource(pages[index].image),
                    modifier = Modifier.aspectRatio(1f),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.padding(32.dp))
                Text(
                    pages[index].subtitle.uppercase(),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (pagerState.currentPage == pages.lastIndex) {
                HabitsButton(title = "Get Started"){ onFinish() }
            } else {
                TextButton(onClick = { onFinish() }) {
                    Text(text = "Skip", color = MaterialTheme.colorScheme.tertiary)
                }
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = MaterialTheme.colorScheme.tertiary,
                    inactiveColor = MaterialTheme.colorScheme.primary
                )
                TextButton(onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }) {
                    Text(text = "Next", color = MaterialTheme.colorScheme.tertiary)
                }
            }
        }
    }
}