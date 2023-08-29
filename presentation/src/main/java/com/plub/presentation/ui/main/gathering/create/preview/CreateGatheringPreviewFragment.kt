package com.plub.presentation.ui.main.gathering.create.preview

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringPreviewBinding
import com.plub.presentation.ui.common.bindingAdapter.setImageFile
import com.plub.presentation.ui.common.bindingAdapter.setImageUrl
import com.plub.presentation.ui.main.gathering.create.CreateGatheringEvent
import com.plub.presentation.ui.main.gathering.create.CreateGatheringViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateGatheringPreviewFragment : BaseFragment<
        FragmentCreateGatheringPreviewBinding, CreateGatheringPreviewPageState, CreateGatheringPreviewViewModel>(
    FragmentCreateGatheringPreviewBinding::inflate
) {
    override val viewModel: CreateGatheringPreviewViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({ requireParentFragment() })

    override fun initView() {
        binding.apply {
            vm = viewModel
            parentVm = parentViewModel

        }

        viewModel.updateMyInfoUrl()
        viewModel.updateDefaultImage(parentViewModel.getSelectedHobby().categoriesSelectedVo)
    }

    override fun onResume() {
        super.onResume()

        parentViewModel.updateChildrenPageState()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect{
                    imageSetting(it.defaultImage)
                }
            }

            launch {
                parentViewModel.eventFlow.collect {
                    if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@collect

                    when (it) {
                        is CreateGatheringEvent.GoToPrevPage -> {
                            parentViewModel.goToPrevPageAndEmitChildrenPageState()
                        }
                    }
                }
            }
        }
    }

    private fun imageSetting(image : String){
        val imageFile = parentViewModel.getPlubbingImage().gatheringImage
        binding.apply {
            if(imageFile == null) imageViewPlubbingMain.setImageUrl(image)
            else imageViewPlubbingMain.setImageFile(imageFile)
        }
    }
}