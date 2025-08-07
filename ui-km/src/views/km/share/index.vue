<template>

  <div class="cloudIndex-div">

    <div class="a-tree">
        <a-typography >
        <a-typography-title :heading="6" style="margin-left: 16px">
          <icon-apps />
          {{kmName}}
        </a-typography-title>
        </a-typography>
      <a-divider style="margin: 5px 0px 10px 0px" class="half-divider"/>
      <a-tree
          v-if="treeData.length"
          v-model:selectedKeys="selectedKeys"
          :show-line="true"
          class="tree-demo"
          :checkable="checked"
          :data="treeData"
          @select="getSelectedNodes"
          @drop="onDrop"
          @drag-end="dragEnd"
          :default-expand-all="true"
          :block-node="true"
      >
        <template #switcher-icon="node, { isLeaf }">
          <IconDown v-if="!isLeaf" />
          <icon-minus :style="{width:'5px'}" v-if="isLeaf" />
        </template>

      </a-tree>
    </div>
    <div class="menu-search">
      <iframe
          :src="dynamicSrc"
          width="100%"
          height="100%"
          frameborder="0"
      ></iframe>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, defineProps, watchEffect } from 'vue';
import { Message, TreeNodeData } from '@arco-design/web-vue';
import {
  listTreeData,
  updateTreeData,
  addFolderFile,
  getKmShareInfo
} from '@/api/dashboard/index';

const isLeaf    = ref(true);
const kmName    = ref('');
const editorURL = ref('');
const shareId   = ref('');

const props = defineProps({
  itemName: { type: String, required: true },
  itemId: { type: String, required: true }
});

const defaultTreeData = [
  {
    title: '新增模板',
    key: '0-0'
  }
];

const links = [
  {
    text: 'workplace.delete',
    icon: 'icon-minus-circle'
  }
];
const selectedFileId = ref('');
const renameInputValue = ref('');
const selectedKeys = ref<string[]>([]);

const treeData = ref([]);
const checked = ref(false);
const onChildClick = async () => {
  const urlParams = new URLSearchParams(window.location.search);
  shareId.value = urlParams.get("id");
  const resKmShare = await getKmShareInfo(shareId.value);
  const kmId = resKmShare.data.linkKmId;
  editorURL.value = resKmShare.data.editorURL;

  const params = {
    id: kmId
  };

  // @ts-ignore
  const res = await listTreeData(params);
  // @ts-ignore
  const { fileKmTreeData,name } = res.data;
  treeData.value = fileKmTreeData
      ? JSON.parse(fileKmTreeData)
      : defaultTreeData;
  kmName.value = name;

  console.log(JSON.parse(fileKmTreeData)[0].children[0]);
};

onChildClick();

const dynamicSrc = ref('');
// onMounted(() => {
//   onChildClick();
// });
watchEffect(() => {
  if (props.itemName) {
    onChildClick();
  }
  if (selectedFileId.value) {
     dynamicSrc.value = `${editorURL.value}?server=content&mode=preview&shareId=${shareId.value}&fileId=${selectedFileId.value}&kmType=km`;
  }

  console.log(dynamicSrc.value);
});
const updateTree = async () => {
  const params = {
    id: props.itemId,
    fileKmTreeData: JSON.stringify(treeData.value)
  };
  // @ts-ignore
  await updateTreeData(params);
  await onChildClick();
};
const findNodeByKey = (data: any[], key: string): any | null => {
  const stack = [...data];
  while (stack.length > 0) {
    const node = stack.pop();
    if (node.key === key) {
      return node;
    }
    if (node.children) {
      stack.push(...node.children);
    }
  }
  return null;
};

const findParentNode = (data: any[], key: string): any | null => {
  const stack = [...data];
  while (stack.length > 0) {
    const node = stack.pop();
    if (
        node.children &&
        node.children.some((child: any) => child.key === key)
    ) {
      return node;
    }
    if (node.children) {
      stack.push(...node.children);
    }
  }
  return null;
};

const getSelectedNodes = (
    newSelectedKeys: (string | number)[],
    data: {
      selected?: boolean;
      selectedNodes: TreeNodeData[];
      node?: TreeNodeData;
      e?: Event;
    }
) => {
  if (data.node) {
    // @ts-ignore
    renameInputValue.value = data.node.title;
    // @ts-ignore
    selectedFileId.value = data.node.id ?? null;
  }
};

const renameClick = (nodeData: { title: string }) => {
  renameInputValue.value = nodeData.title;
  // renameDisabled.value = false;
};
// @ts-ignore

const dragEnd = () => {
  updateTree();
};
// @ts-ignore
const onDrop = ({ dragNode, dropNode, dropPosition }) => {
  const data = treeData.value;
  // @ts-ignore
  const loop = (datas, key, callback) => {
    datas.some((item: { key: any; children: any }, index: any, arr: any) => {
      if (item.key === key) {
        callback(item, index, arr);
        return true;
      }
      if (item.children) {
        return loop(item.children, key, callback);
      }
      return false;
    });
  };

  loop(data, dragNode.key, (_: any, index: any, arr: any[]) => {
    arr.splice(index, 1);
  });

  if (dropPosition === 0) {
    loop(data, dropNode.key, (item: { children: any[] }) => {
      item.children = item.children || [];
      item.children.push(dragNode);
    });
  } else {
    loop(data, dropNode.key, (_: any, index: number, arr: any[]) => {
      arr.splice(dropPosition < 0 ? index : index + 1, 0, dragNode);
    });
  }
};

document.body.setAttribute('arco-theme', 'dark');
document.getElementById("app").style.height="calc(100% - 20px)";

</script>

<style lang="less" scoped>


.cloudIndex-div {
  height: 100%;
  display: flex;
  flex-grow: 1;
  border-left: rgb(var(--grey-3));
  background-color: var(--color-bg-2);
}

.a-tree {
  border-right: 1px solid var(--color-border);
  min-width: 260px;
  padding-left: 2px;
}
:deep(.arco-tree),
:deep(.arco-tree-size-medium),
:deep(.tree-demo) {
  min-width: 200px;
  max-width: 200px;
  margin-left: 16px;
}
.tree-demo :deep(.tree-node-dropover) > :deep(.arco-tree-node-title),
.tree-demo :deep(.tree-node-dropover) > :deep(.arco-tree-node-title):hover {
  animation: blinkBg 0.4s 2;
}

@keyframes blinkBg {
  0% {
    background-color: transparent;
  }

  100% {
    background-color: var(--color-primary-light-1);
  }
}
.dropdown-div {
  display: flex;
  width: 121px;
  align-items: center;
  margin-bottom: 0px;
  gap: 10px;
  .text {
    color: var(--color-text-1);
    font-size: 11.5px;
    font-style: normal;
    font-weight: 800;
    line-height: normal;
  }
}
.menu-search {
  flex-grow: 1;
  height: 100%;
  .search {
    padding-left: 18px;
    margin-right: 20px;
    font-size: 14px;
    padding-top: 5px;
    background-color: var(--color-bg-2);
  }
  border-left: rgb(var(--grey-3));
  border-bottom: rgb(var(--grey-3));
}
:deep(.arco-input-wrapper .arco-input.arco-input-size-large) {
  font-size: 17px;
}
</style>
