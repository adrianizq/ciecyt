import { createLocalVue, shallowMount, Wrapper } from '@/shared/test/test-utils';
import FileUpload from '@/shared/components/file-upload.vue';

const localVue = createLocalVue();

describe('FileUpload component (Sprint 4)', () => {
  function makeFile(opts: { name?: string; type?: string; size?: number } = {}): File {
    const blob = new Blob(['x'.repeat(opts.size ?? 100)], { type: opts.type ?? 'application/pdf' });
    return new File([blob], opts.name ?? 'doc.pdf', { type: opts.type ?? 'application/pdf' });
  }

  function mount(props: Record<string, unknown>) {
    return shallowMount<any>(FileUpload, {
      localVue,
      propsData: {
        label: 'Adjuntar',
        ...props,
      },
    });
  }

  it('accepts a valid file within size and type', async () => {
    const wrapper: Wrapper<any> = mount({ accept: '.pdf', maxBytes: 1024 });
    const input = wrapper.find('input[type=file]').element as HTMLInputElement;
    const file = makeFile({ name: 'test.pdf', type: 'application/pdf' });
    Object.defineProperty(input, 'files', { value: [file], configurable: true });
    await input.dispatchEvent(new Event('change'));
    expect(wrapper.vm.file).toBeTruthy();
    expect(wrapper.vm.error).toBe('');
  });

  it('rejects when type does not match accept', async () => {
    const wrapper = mount({ accept: '.pdf' });
    const input = wrapper.find('input[type=file]').element as HTMLInputElement;
    const file = makeFile({ name: 'image.png', type: 'image/png' });
    Object.defineProperty(input, 'files', { value: [file], configurable: true });
    await input.dispatchEvent(new Event('change'));
    expect(wrapper.vm.error).toMatch(/no permitido/i);
    expect(wrapper.vm.file).toBeNull();
  });

  it('rejects when size exceeds maxBytes', async () => {
    const wrapper = mount({ accept: '.pdf', maxBytes: 100 });
    const input = wrapper.find('input[type=file]').element as HTMLInputElement;
    const file = makeFile({ name: 'big.pdf', size: 1024 });
    Object.defineProperty(input, 'files', { value: [file], configurable: true });
    await input.dispatchEvent(new Event('change'));
    expect(wrapper.vm.error).toMatch(/demasiado grande/i);
    expect(wrapper.vm.file).toBeNull();
  });

  it('accepts wildcard image/* with image/png', async () => {
    const wrapper = mount({ accept: 'image/*' });
    const input = wrapper.find('input[type=file]').element as HTMLInputElement;
    const file = makeFile({ name: 'img.png', type: 'image/png' });
    Object.defineProperty(input, 'files', { value: [file], configurable: true });
    await input.dispatchEvent(new Event('change'));
    expect(wrapper.vm.error).toBe('');
  });

  it('clear resets file and error', async () => {
    const wrapper = mount({ accept: '.pdf' });
    const input = wrapper.find('input[type=file]').element as HTMLInputElement;
    const file = makeFile();
    Object.defineProperty(input, 'files', { value: [file], configurable: true });
    await input.dispatchEvent(new Event('change'));
    wrapper.vm.clear();
    expect(wrapper.vm.file).toBeNull();
    expect(wrapper.vm.error).toBe('');
  });
});
