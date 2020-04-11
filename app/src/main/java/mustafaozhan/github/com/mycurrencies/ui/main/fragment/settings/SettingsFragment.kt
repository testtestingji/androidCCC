package mustafaozhan.github.com.mycurrencies.ui.main.fragment.settings

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.github.mustafaozhan.basemob.fragment.BaseDBFragment
import mustafaozhan.github.com.mycurrencies.R
import mustafaozhan.github.com.mycurrencies.databinding.FragmentSettingsBinding
import mustafaozhan.github.com.mycurrencies.extension.gone
import mustafaozhan.github.com.mycurrencies.extension.reObserve
import mustafaozhan.github.com.mycurrencies.tool.Toasty.showToasty
import mustafaozhan.github.com.mycurrencies.ui.main.fragment.settings.view.FewCurrency
import mustafaozhan.github.com.mycurrencies.ui.main.fragment.settings.view.SettingsViewEvent
import javax.inject.Inject

/**
 * Created by Mustafa Ozhan on 2018-07-12.
 */
class SettingsFragment : BaseDBFragment<FragmentSettingsBinding>() {

    @Inject
    lateinit var settingsViewModel: SettingsViewModel

    private lateinit var viewEvent: SettingsViewEvent

    private val settingsAdapter: SettingsAdapter by lazy { SettingsAdapter(viewEvent) }

    override fun bind(container: ViewGroup?): FragmentSettingsBinding =
        FragmentSettingsBinding.inflate(layoutInflater, container, false)

    override fun onBinding(dataBinding: FragmentSettingsBinding) {
        binding.viewModel = settingsViewModel
        settingsViewModel.getViewEvent().let {
            binding.viewEvent = it
            viewEvent = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBaseActivity()?.setSupportActionBar(binding.toolbarFragmentSettings)
        initViews()
        initList()
        initViewEffect()
    }

    private fun initList() = settingsViewModel.viewState.apply {
        currencyList.reObserve(viewLifecycleOwner, Observer {
            binding.txtNoResult.gone()
            settingsAdapter.submitList(it)
        })
    }

    private fun initViewEffect() = settingsViewModel.viewEffectLiveData
        .reObserve(viewLifecycleOwner, Observer { viewEvent ->
            when (viewEvent) {
                FewCurrency -> showToasty(requireContext(), R.string.choose_at_least_two_currency)
            }
        })

    private fun initViews() = with(binding) {
        recyclerViewSettings.apply {
            setHasFixedSize(true)
            adapter = settingsAdapter
        }
    }
}
